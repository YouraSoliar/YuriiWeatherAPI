package com.example.yuriiweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
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

    private static final String API_KEY = "7a56f2fc25654cf1aaa180014221611";
    private static final int COUNT = 14;
    private static final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json?key="
            + API_KEY + "&q=%s&days=" + COUNT;

    private TextView textViewMax;
    private TextView textViewMin;
    private TextView textViewAvg;
    private TextView textViewDate;
    private TextView textViewFall;
    private EditText editTextCity;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initView();
    }

    public void initView() {
        textViewAvg = findViewById(R.id.textViewAvg);
        textViewMin = findViewById(R.id.textViewMin);
        textViewMax = findViewById(R.id.textViewMax);
        textViewDate = findViewById(R.id.textViewDate);
        textViewFall = findViewById(R.id.textViewFall);
        editTextCity = findViewById(R.id.editTextCity);
    }

    private class GetUrlData extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String weather = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                    String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
                    String sunset = String.valueOf(jsonObject.getJSONObject("sys").getLong("sunset")).substring(0, 10);
                    String sunrise = String.valueOf(jsonObject.getJSONObject("sys").getLong("sunrise")).substring(0, 10);
                    long unixSunset = Long.parseLong(sunset);
                    long unixSunrise = Long.parseLong(sunrise);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }
}