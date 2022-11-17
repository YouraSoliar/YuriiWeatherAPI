package com.example.yuriiweatherapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherDay> weathers = new ArrayList<>();

    public List<WeatherDay> getWeathers() {
        return new ArrayList<>(weathers);
    }

    public void setWeathers(List<WeatherDay> weathers) {
        this.weathers = weathers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent,false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDay weather = weathers.get(position);

        holder.textViewMax.setText((int) weather.getTemperature().getMaxTemperature());
        holder.textViewMin.setText((int) weather.getTemperature().getMinTemperature());
        holder.textViewAvg.setText((int) weather.getTemperature().getAvgTemperature());
        holder.textViewDate.setText(weather.getDate());
        if (weather.getTemperature().isRain() == 1) {
            holder.textViewFall.setText(R.string.rain);
        } else if (weather.getTemperature().isSnow() == 1) {
            holder.textViewFall.setText(R.string.snow);
        } else {
            holder.textViewFall.setText(R.string.none);
        }

    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewMax;
        private TextView textViewMin;
        private TextView textViewAvg;
        private TextView textViewDate;
        private TextView textViewFall;

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
