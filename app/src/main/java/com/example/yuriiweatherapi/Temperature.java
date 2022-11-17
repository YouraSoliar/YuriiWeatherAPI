package com.example.yuriiweatherapi;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("maxtemp_c")
    private double maxTemperature;
    @SerializedName("mintemp_c")
    private double minTemperature;
    @SerializedName("avgtemp_c")
    private double avgTemperature;
    @SerializedName("daily_will_it_rain")
    private int isRain;
    @SerializedName("daily_will_it_snow")
    private int isSnow;

    public Temperature(double maxTemperature, double minTemperature, double avgTemperature, int isRain, int isSnow) {
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

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }
}
