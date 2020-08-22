package com.venten.venten.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.venten.venten.model.data.FilterResponseModel;
import com.venten.venten.model.service.VentenApiService;
import com.venten.venten.model.sql.VentenDao;
import com.venten.venten.model.sql.VentenDatabase;
import com.venten.venten.util.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FilterViewModel extends AndroidViewModel {

    public MutableLiveData<List<FilterResponseModel>> filters = new MutableLiveData<List<FilterResponseModel>>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> filterLoadError = new MutableLiveData<Boolean>();

    private AsyncTask<List<FilterResponseModel>, Void, List<FilterResponseModel>> insertFilterTask;
    private AsyncTask<Void, Void, List<FilterResponseModel>> retrieveFilterTask;

    private SharedPreferenceHelper sharedPreferencesHelper = SharedPreferenceHelper.getInstance(getApplication());

    //Time in Nano Seconds
    private Long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    private VentenApiService apiService = new VentenApiService();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FilterViewModel(@NonNull Application application) {
        super(application);
    }

    private void checkCacheDuration(){
        String cachePreference = sharedPreferencesHelper.getCachedDuration();
        if(!cachePreference.equals("")){
            try{
                int cachepref = Integer.parseInt(cachePreference);
                refreshTime = cachepref * 1000 * 1000 * 1000L;
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }

    public void refresh() {
        checkCacheDuration();
        long updateTime = sharedPreferencesHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFilterFromDatabase();
        } else {
            fetchFilterFromRemote();
        }
    }

    public void refreshByPassCache(){
        fetchFilterFromRemote();
    }

    private void fetchFilterFromDatabase(){
        loading.setValue(true);
        retrieveFilterTask = new RetrieveFilterTask();
        retrieveFilterTask.execute();

    }

    private void filterRetrieved(List<FilterResponseModel> filterResponseModelList){
        filters.setValue(filterResponseModelList);
        filterLoadError.setValue(false);
        loading.setValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        if(insertFilterTask != null){
            insertFilterTask.cancel(true);
            insertFilterTask = null;
        }

        if(retrieveFilterTask != null){
            retrieveFilterTask.cancel(true);
            retrieveFilterTask = null;
        }
    }

    private class InsertFilterTask extends AsyncTask<List<FilterResponseModel>, Void, List<FilterResponseModel> >{

        @Override
        protected List<FilterResponseModel> doInBackground(List<FilterResponseModel>... lists) {
            List<FilterResponseModel> list = lists[0];
            VentenDao ventenDao = VentenDatabase.getInstance(getApplication()).ventenDao();
            ventenDao.deleteAllFilters();

            ArrayList<FilterResponseModel> newList = new ArrayList<>(list);
            List<Long> result = ventenDao.insertAll(newList.toArray(new FilterResponseModel[0]));

            int i = 0;
            while(i < list.size()){
                list.get(i).Uuid = result.get(i).intValue();
                ++i;
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<FilterResponseModel> filterResponseModelList) {
            super.onPostExecute(filterResponseModelList);
            filterRetrieved(filterResponseModelList);
            sharedPreferencesHelper.saveUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveFilterTask extends AsyncTask<Void, Void,List<FilterResponseModel>>{

        @Override
        protected List<FilterResponseModel> doInBackground(Void... voids) {
            return VentenDatabase.getInstance(getApplication()).ventenDao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<FilterResponseModel> filterResponseModelList) {
            super.onPostExecute(filterResponseModelList);
            filterRetrieved(filterResponseModelList);
            Toast.makeText(getApplication(),"Fetching data from database", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchFilterFromRemote() {
        loading.setValue(true);
        compositeDisposable.add(
                apiService.getFilters()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<FilterResponseModel>>() {
                            @Override
                            public void onSuccess(List<FilterResponseModel> dogBreeds) {
                                insertFilterTask = new InsertFilterTask();
                                insertFilterTask.execute(dogBreeds);
                                Toast.makeText(getApplication(),"Fetching data from endpoint", Toast.LENGTH_LONG).show();
                                //NotificationsHelper.getInstance(getApplication()).createNotification();
                            }

                            @Override
                            public void onError(Throwable e) {
                                filterLoadError.setValue(true);
                                loading.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }
}
