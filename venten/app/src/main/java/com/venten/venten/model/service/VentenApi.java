package com.venten.venten.model.service;

import com.venten.venten.model.data.FilterResponseModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface VentenApi {

    @GET("assessment/filter.json")
    Single<List<FilterResponseModel>> getFilters();
}
