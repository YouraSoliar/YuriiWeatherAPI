package com.example.yuriiweatherapi;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    private final MutableLiveData<List<WeatherDay>> weatherDays = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<WeatherDay>> getWeatherDay() {
        return weatherDays;
    }

//    public String getLocation() {
//
//    }

    public void loadWeatherDay(String city) {
        Disposable disposable = ApiFactory.apiService.loadWeatherDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResponse>() {
                    @Override
                    public void accept(WeatherResponse weatherResponse) throws Throwable {
                        weatherDays.setValue(weatherResponse.getWeatherDaysResponse().getWeatherDayList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.getMessage());
                    }
                });


        compositeDisposable.add(disposable);

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}