package com.example.yuriiweatherapi.model;

import com.google.gson.annotations.SerializedName;

public class WeatherDay {

    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Temperature temperature;

    public WeatherDay(String date, Temperature temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public String getDate() {
        return date;
    }


}
