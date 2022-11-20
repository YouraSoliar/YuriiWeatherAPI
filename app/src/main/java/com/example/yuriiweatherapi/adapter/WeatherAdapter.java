package com.example.yuriiweatherapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yuriiweatherapi.R;
import com.example.yuriiweatherapi.model.WeatherDay;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherDay> weathers = new ArrayList<>();

    public void setWeathers(List<WeatherDay> weathersVM) {
        this.weathers = weathersVM;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.weather_item, parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDay weather = weathers.get(position);

        holder.textViewMax.setText(weather.getTemperature().getMaxTemperature());
        holder.textViewMin.setText(weather.getTemperature().getMinTemperature());
        holder.textViewAvg.setText(weather.getTemperature().getAvgTemperature());
        holder.textViewDate.setText(weather.getDate());
        if (weather.getTemperature().isSnow() == 1) {
            holder.textViewFall.setText(R.string.snow);
        } else if (weather.getTemperature().isRain() == 1) {
            holder.textViewFall.setText(R.string.rain);
        } else {
            holder.textViewFall.setText(R.string.none);
        }
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewMax;
        private final TextView textViewMin;
        private final TextView textViewAvg;
        private final TextView textViewDate;
        private final TextView textViewFall;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAvg = itemView.findViewById(R.id.textViewAvg);
            textViewMin = itemView.findViewById(R.id.textViewMin);
            textViewMax = itemView.findViewById(R.id.textViewMax);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewFall = itemView.findViewById(R.id.textViewFall);
        }
    }
}
