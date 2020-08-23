package com.venten.venten.model.data;

/*
  This call take all parameters from the filter object to perform
  search in the car data.
 */
public class FilterModel {

    private String gender;
    private String countries;
    private String colors;
    private Integer startYear;
    private Integer endYear;

    @Override
    public String toString() {
        return "FilterModel{" +
                "gender='" + gender + '\'' +
                ", countries='" + countries + '\'' +
                ", colors='" + colors + '\'' +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                '}';
    }

    public FilterModel(Integer startYear, Integer endYear, String gender, String countries, String colors) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.gender = gender;
        this.countries = countries;
        this.colors = colors;
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

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }


}
