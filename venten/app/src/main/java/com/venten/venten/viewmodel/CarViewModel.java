package com.venten.venten.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.venten.venten.R;
import com.venten.venten.model.data.CarDataModel;
import com.venten.venten.model.data.FilterModel;
import com.venten.venten.model.data.FilterResponseModel;
import com.venten.venten.model.sql.VentenDatabase;
import com.venten.venten.util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class CarViewModel extends AndroidViewModel {

    public MutableLiveData<List<CarDataModel>> mutableLiveData = new MutableLiveData<List<CarDataModel>>();
    public MutableLiveData<FilterResponseModel>  filterResponseMutableLiveData = new MutableLiveData<FilterResponseModel>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> filterLoadError = new MutableLiveData<Boolean>();

    private List<CarDataModel> carDataModelList = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AsyncTask<List<CarDataModel>, Void, List<CarDataModel>> searchDataTask;
    private RetrieveFilterTask retrieveFilterTask;

    public CarViewModel(@NonNull Application application) {
        super(application);
    }

    private List<CarDataModel> readCarData(FilterModel filterModel){
       // loading.setValue(true);
        CarDataModel carDataModel = null;
        InputStream inputStream = getApplication().getResources().openRawResource(R.raw.car_ownsers_data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
                Charset.forName("UTF-8")));

        String line = "";
        try {
            //Setp over the headers
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                //Split the content by commas
                String[] tokens = line.split(",");

                //read the data and checks for filter condition.
                if(tokens[7].contains(filterModel.getColors()) || tokens[4].contains(filterModel.getCountries())
                        || tokens[8].equalsIgnoreCase(filterModel.getGender())){
                     if(Utils.isNumber(String.valueOf(filterModel.getStartYear())) && Utils.isNumber(String.valueOf(filterModel.getEndYear()))){
                         if(Integer.parseInt(tokens[6]) >= filterModel.getStartYear() &&
                                 Integer.parseInt(tokens[6]) <= filterModel.getEndYear()){
                             carDataModel = new CarDataModel(tokens[0],tokens[1],tokens[2],tokens[3]
                                     ,tokens[4],tokens[5],tokens[6],tokens[7],tokens[8],tokens[9],tokens[10]);
                             carDataModelList.add(carDataModel);
                         }
                     }

                }
                Log.d("CarViewModel", "My Car data " + carDataModel );
            }
        } catch (IOException e) {
            Log.wtf("CarViewModel","Error reading data file on line " +  line, e);
            filterLoadError.setValue(true);
            e.printStackTrace();
        }
        return carDataModelList;
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
        if(searchDataTask != null){
            searchDataTask.cancel(true);
            searchDataTask = null;
        }
    }

    private void carDataRetrieved(List<CarDataModel> carDataModelList){
        mutableLiveData.setValue(carDataModelList);
        filterLoadError.setValue(false);
        loading.setValue(false);
    }


    private class SearchDataTask extends AsyncTask<List<CarDataModel>, Void, List<CarDataModel> > {

        private FilterModel filterModel;

        public SearchDataTask(FilterModel filterModel1){
            filterModel = filterModel1;
        }

        @Override
        protected List<CarDataModel> doInBackground(List<CarDataModel>... lists) {
            List<CarDataModel> carDataModelList = readCarData(filterModel);
            return carDataModelList;
        }

        @Override
        protected void onPostExecute(List<CarDataModel> carDataModels) {
            super.onPostExecute(carDataModels);
            carDataRetrieved(carDataModels);
        }

    }

    private void  searchCar(FilterModel filterModel){
        searchDataTask = new SearchDataTask(filterModel);
        searchDataTask.execute();
    }

    private class RetrieveFilterTask extends AsyncTask<Integer, Void, FilterResponseModel>{

        @Override
        protected FilterResponseModel doInBackground(Integer... integers) {
            int uuid = integers[0];
            return VentenDatabase.getInstance(getApplication()).ventenDao().getFilters(uuid);
        }

        @Override
        protected void onPostExecute(FilterResponseModel filterResponseModel) {
            super.onPostExecute(filterResponseModel);

            FilterModel filterModel = new FilterModel(filterResponseModel.getStartYear(),
                    filterResponseModel.getEndYear(),filterResponseModel.getGender(),
                    filterResponseModel.getElements(filterResponseModel.getCountries()),
                    filterResponseModel.getElements(filterResponseModel.getColors()));
            searchCar(filterModel);
            filterResponseMutableLiveData.setValue(filterResponseModel);
        }
    }

    public void fetch(int uuid){
        retrieveFilterTask = new RetrieveFilterTask();
        retrieveFilterTask.execute(uuid);
    }


}
