package com.example.yuriiweatherapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDaysResponse {

    @SerializedName("forecastday")
    private List<WeatherDay> weatherDayList;

    public WeatherDaysResponse(List<WeatherDay> weatherDayList) {
        this.weatherDayList = weatherDayList;
    }

    public List<WeatherDay> getWeatherDayList() {
        return weatherDayList;
    }
}
