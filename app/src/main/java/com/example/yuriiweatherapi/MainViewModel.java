package com.example.yuriiweatherapi;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "WeatherAPI";

    private MutableLiveData<List<WeatherDay>> weatherDays = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<WeatherDay>> getWeatherDay() {
        return weatherDays;
    }

    public void loadWeatherDay(String city) {
        Disposable disposable = loadWeatherDayRx(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WeatherDay>>() {
                    @Override
                    public void accept(List<WeatherDay> weathers) throws Throwable {
                        weatherDays.setValue(weathers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(getApplication(), "error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);

    }

    private Single<List<WeatherDay>> loadWeatherDayRx(String city) {
        return ApiFactory.getApiService().loadWeatherDay(city);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}