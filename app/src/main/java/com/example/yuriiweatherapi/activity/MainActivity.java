package com.example.yuriiweatherapi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yuriiweatherapi.R;
import com.example.yuriiweatherapi.adapter.WeatherAdapter;
import com.example.yuriiweatherapi.model.MainViewModel;
import com.example.yuriiweatherapi.response.WeatherDay;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewFind;
    private TextView textViewLocation;
    private RecyclerView recyclerViewWeather;
    private WeatherAdapter adapter;
    private static final String TAG = "WeatherAPI";
    private MainViewModel viewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(MainActivity.this);

        initView();
        initAction();
    }

    public void initView() {
        editTextCity = findViewById(R.id.editTextCity);
        textViewFind = findViewById(R.id.textViewFind);
        textViewLocation = findViewById(R.id.textViewLocation);
        recyclerViewWeather = findViewById(R.id.recyclerViewWeather);
        adapter = new WeatherAdapter();
        recyclerViewWeather.setAdapter(adapter);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.orange)));
    }

    public void initAction() {

        viewModel.getCity().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String city) {
                editTextCity.setText(city);
            }
        });

        viewModel.getWeatherDay().observe(this, new Observer<List<WeatherDay>>() {
            @Override
            public void onChanged(List<WeatherDay> weatherDays) {
                adapter.setWeathers(weatherDays);
            }
        });

        textViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadWeatherDay(editTextCity.getText().toString());
            }
        });

        textViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPermission = checkPermission();
                if (isPermission) {
                    viewModel.getLocation();
                }
            }
        });
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);
            return false;
        } else {
            return true;
        }
    }
}