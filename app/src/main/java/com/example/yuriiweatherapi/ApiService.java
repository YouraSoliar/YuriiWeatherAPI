package com.example.yuriiweatherapi;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast.json?key=7a56f2fc25654cf1aaa180014221611&days=14&q=lviv")
    Single<WeatherResponse> loadWeatherDay();
}
