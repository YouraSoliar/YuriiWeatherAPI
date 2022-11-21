package com.example.yuriiweatherapi.domain.api;

import com.example.yuriiweatherapi.domain.models.WeatherResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast.json?key=7a56f2fc25654cf1aaa180014221611&days=14")
    Single<WeatherResponse> loadWeatherDay(@Query("q") String city);
}
