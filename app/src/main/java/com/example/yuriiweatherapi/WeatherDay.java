package com.example.yuriiweatherapi;

public class WeatherDay {
    private double maxTemperature;
    private double minTemperature;
    private double avgTemperature;
    private String date;
    private int isRain;
    private int isSnow;

    public WeatherDay(double maxTemperature, double minTemperature, double avgTemperature, String date, int isRain, int isSnow) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.avgTemperature = avgTemperature;
        this.date = date;
        this.isRain = isRain;
        this.isSnow = isSnow;
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

    public String getDate() {
        return date;
    }

    public int isRain() {
        return isRain;
    }

    public int isSnow() {
        return isSnow;
    }
}
