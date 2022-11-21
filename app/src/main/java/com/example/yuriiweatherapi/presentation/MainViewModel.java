package com.example.yuriiweatherapi.presentation;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yuriiweatherapi.R;
import com.example.yuriiweatherapi.domain.api.ApiFactory;
import com.example.yuriiweatherapi.domain.models.City;
import com.example.yuriiweatherapi.domain.models.WeatherDay;
import com.example.yuriiweatherapi.domain.models.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "WeatherAPI";

    private final MutableLiveData<List<WeatherDay>> weatherDays = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<City> city = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<WeatherDay>> getWeatherDay() {
        return weatherDays;
    }

    public LiveData<City> getCity() {
        return city;
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices
                    .getFusedLocationProviderClient(getApplication());
            fusedLocationProviderClient
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getApplication(), Locale.ENGLISH);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1);
                            String yourCity = addresses.get(0).getLocality();
                            if (yourCity.contains("'")) {
                                yourCity = yourCity.replace("'", "");
                            }
                            city.setValue(new City(yourCity));
                            loadWeatherDay(new City(yourCity));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void loadWeatherDay(City city) {
        if (city.getCity().equals("")) {
            Toast.makeText(getApplication(), R.string.toast_fill_field, Toast.LENGTH_SHORT).show();
        } else {
            Disposable disposable = ApiFactory.apiService.loadWeatherDay(city.getCity())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WeatherResponse>() {
                        @Override
                        public void accept(WeatherResponse weatherResponse) throws Throwable {
                            weatherDays.setValue(weatherResponse
                                    .getWeatherDaysResponse()
                                    .getWeatherDayList());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Throwable {
                            Log.d(TAG, throwable.getMessage());
                            if (!isNetworkAvailable(getApplication())) {
                                Toast.makeText(getApplication(),
                                        R.string.toast_no_internet,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplication(),
                                        R.string.toast_no_city,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            compositeDisposable.add(disposable);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}