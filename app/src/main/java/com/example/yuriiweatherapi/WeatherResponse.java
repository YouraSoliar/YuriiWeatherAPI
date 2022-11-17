package com.example.yuriiweatherapi;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("forecast")
    private WeatherDaysResponse weatherDaysResponse;

    public WeatherResponse(WeatherDaysResponse weatherDaysResponse) {
        this.weatherDaysResponse = weatherDaysResponse;
    }

    public WeatherDaysResponse getWeatherDaysResponse() {
        return weatherDaysResponse;
    }
}
