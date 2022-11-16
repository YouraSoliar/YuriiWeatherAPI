package com.example.yuriiweatherapi;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String API_KEY = "7a56f2fc25654cf1aaa180014221611";
    private static final int COUNT = 14;
    private static final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json?key="
            + API_KEY + "&q=%s&days=" + COUNT;

    private MutableLiveData<WeatherDay> weatherDay = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<WeatherDay> getWeatherDay() {
        return weatherDay;
    }

    public void loadWeatherDay(String city) {
        Disposable disposable = loadWeatherDayRx(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherDay>() {
                    @Override
                    public void accept(WeatherDay weather) throws Throwable {
                        weatherDay.setValue(weather);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(getApplication(), "error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);

    }

    private Single<WeatherDay> loadWeatherDayRx(String city) {
        return ApiFactory.getApiService().loadWeatherDay(city);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}