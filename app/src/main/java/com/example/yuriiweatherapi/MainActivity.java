package com.example.yuriiweatherapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
    }

    public void initAction() {
        viewModel.getWeatherDay().observe(this, new Observer<List<WeatherDay>>() {
            @Override
            public void onChanged(List<WeatherDay> weatherDays) {
                adapter.setWeathers(weatherDays);
            }
        });
        textViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextCity.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, getText(R.string.toast_fill_field), Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.loadWeatherDay(editTextCity.getText().toString());
                }
            }
        });
        textViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    editTextCity.setText(addresses.get(0).getLocality());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 11);
                }
            }
        });
    }
}