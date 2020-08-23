package com.venten.venten.model.data;

public class CarDataModel {

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String country;
    private String car_model;
    private String car_model_year;
    private String car_color;
    private String gender;
    private String job_title;
    private String bio;

    public CarDataModel(String id, String first_name, String last_name, String email, String country,
                        String car_model, String car_model_year, String car_color, String gender,
                        String job_title, String bio) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.country = country;
        this.car_model = car_model;
        this.car_model_year = car_model_year;
        this.car_color = car_color;
        this.gender = gender;
        this.job_title = job_title;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_model_year() {
        return car_model_year;
    }

    public void setCar_model_year(String car_model_year) {
        this.car_model_year = car_model_year;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "CarDataModel{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", car_model='" + car_model + '\'' +
                ", car_model_year='" + car_model_year + '\'' +
                ", car_color='" + car_color + '\'' +
                ", gender='" + gender + '\'' +
                ", job_title='" + job_title + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
