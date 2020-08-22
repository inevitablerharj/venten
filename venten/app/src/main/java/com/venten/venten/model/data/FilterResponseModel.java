package com.venten.venten.model.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FilterResponseModel {

    @PrimaryKey(autoGenerate = true)
    public int Uuid;

    @ColumnInfo(name="id")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name="start_year")
    @SerializedName("start_year")
    @Expose
    private Integer startYear;

    @ColumnInfo(name="end_year")
    @SerializedName("end_year")
    @Expose
    private Integer endYear;

    @ColumnInfo(name="gender")
    @SerializedName("gender")
    @Expose
    private String gender;

    @ColumnInfo(name="countries")
    @SerializedName("countries")
    @Expose
    @TypeConverters(VentenConverters.class)
    private List<String> countries = null;

    @ColumnInfo(name="colors")
    @SerializedName("colors")
    @Expose
    @TypeConverters(VentenConverters.class)
    private List<String> colors = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
