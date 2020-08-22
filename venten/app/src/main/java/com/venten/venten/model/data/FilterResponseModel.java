package com.venten.venten.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterResponseModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("start_year")
    @Expose
    private Integer startYear;
    @SerializedName("end_year")
    @Expose
    private Integer endYear;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("countries")
    @Expose
    private List<String> countries = null;
    @SerializedName("colors")
    @Expose
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
