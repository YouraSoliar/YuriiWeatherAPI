package com.example.yuriiweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMax;
    private TextView textViewMin;
    private TextView textViewAvg;
    private TextView textViewDate;
    private TextView textViewFall;
    private EditText editTextCity;
    private TextView textViewFind;

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
        textViewAvg = findViewById(R.id.textViewAvg);
        textViewMin = findViewById(R.id.textViewMin);
        textViewMax = findViewById(R.id.textViewMax);
        textViewDate = findViewById(R.id.textViewDate);
        textViewFall = findViewById(R.id.textViewFall);
        editTextCity = findViewById(R.id.editTextCity);
        textViewFind = findViewById(R.id.textViewFind);
    }

    public void initAction() {
        viewModel.getWeatherDay().observe(this, new Observer<WeatherDay>() {
            @Override
            public void onChanged(WeatherDay weatherDay) {

            }
        });
        textViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadWeatherDay(editTextCity.getText().toString());

            }
        });
    }
}