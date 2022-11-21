package com.example.yuriiweatherapi.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.yuriiweatherapi.R;
import com.example.yuriiweatherapi.adapter.WeatherAdapter;
import com.example.yuriiweatherapi.databinding.ActivityMainBinding;
import com.example.yuriiweatherapi.domain.models.City;
import com.example.yuriiweatherapi.domain.models.WeatherDay;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter adapter;
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initView();
        initAction();
    }

    public void initView() {
        adapter = new WeatherAdapter();
        binding.recyclerViewWeather.setAdapter(adapter);
        getSupportActionBar()
                .setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(R.color.orange)));
    }

    public void initAction() {

        viewModel.getCity().observe(this, new Observer<City>() {
            @Override
            public void onChanged(City city) {
                binding.editTextCity.setText(city.getCity());
            }
        });

        viewModel.getWeatherDay().observe(this, new Observer<List<WeatherDay>>() {
            @Override
            public void onChanged(List<WeatherDay> weatherDays) {
                adapter.setWeathers(weatherDays);
            }
        });

        binding.textViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadWeatherDay(new City(binding.editTextCity.getText().toString()));
            }
        });

        binding.textViewLocation.setOnClickListener(new View.OnClickListener() {
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