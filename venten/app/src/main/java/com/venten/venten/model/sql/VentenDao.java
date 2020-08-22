package com.venten.venten.model.sql;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.venten.venten.model.data.FilterResponseModel;

import java.util.List;

@Dao
public interface VentenDao {

    @Insert
    List<Long> insertAll(FilterResponseModel... filterResponseModels);

    @Query("SELECT * FROM FilterResponseModel")
    List<FilterResponseModel> getAllDogs();

    @Query("SELECT * FROM FilterResponseModel WHERE id = :id")
    FilterResponseModel getFilters(int id);

    @Query("DELETE FROM FilterResponseModel")
    void deleteAllFilters();


}
