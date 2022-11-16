package com.example.yuriiweatherapi;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("")
    Single<WeatherDay> loadWeatherDay(String city);
}
