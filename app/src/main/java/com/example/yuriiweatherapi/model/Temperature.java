package com.example.yuriiweatherapi.model;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("maxtemp_c")
    private String maxTemperature;
    @SerializedName("mintemp_c")
    private String minTemperature;
    @SerializedName("avgtemp_c")
    private String avgTemperature;
    @SerializedName("daily_will_it_rain")
    private int isRain;
    @SerializedName("daily_will_it_snow")
    private int isSnow;

    public Temperature(String maxTemperature,
                       String minTemperature,
                       String avgTemperature,
                       int isRain,
                       int isSnow) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.avgTemperature = avgTemperature;
        this.isRain = isRain;
        this.isSnow = isSnow;
    }

    public int isRain() {
        return isRain;
    }

    public int isSnow() {
        return isSnow;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public String getAvgTemperature() {
        return avgTemperature;
    }
}
