package com.venten.venten.model.service;

import com.venten.venten.model.data.FilterResponseModel;
import com.venten.venten.util.AppConstant;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class VentenApiService {

    private VentenApi ventenApi;

    public VentenApiService(){

        ventenApi = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(VentenApi.class);
    }

    public Single<List<FilterResponseModel>> getFilters(){
        return ventenApi.getFilters();
    }
}
