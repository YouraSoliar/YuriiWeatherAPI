package com.example.yuriiweatherapi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    private static final String API_KEY = "7a56f2fc25654cf1aaa180014221611";
    private static final int COUNT = 14;
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    public static final ApiService apiService = retrofit.create(ApiService.class);
}
