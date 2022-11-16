package com.example.yuriiweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewFind;
    private RecyclerView recyclerViewWeather;
    private WeatherAdapter adapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initView();
        initAction();
    }

    public void initView() {
        editTextCity = findViewById(R.id.editTextCity);
        textViewFind = findViewById(R.id.textViewFind);
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
    }
}