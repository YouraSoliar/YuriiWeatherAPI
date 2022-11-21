package com.example.yuriiweatherapi.adapter;

        import android.view.LayoutInflater;
        import android.view.ViewGroup;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.yuriiweatherapi.R;
        import com.example.yuriiweatherapi.databinding.WeatherItemBinding;
        import com.example.yuriiweatherapi.domain.models.WeatherDay;

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
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(WeatherItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDay weather = weathers.get(position);

        holder.binding.textViewMax.setText(weather.getTemperature().getMaxTemperature());
        holder.binding.textViewMin.setText(weather.getTemperature().getMinTemperature());
        holder.binding.textViewAvg.setText(weather.getTemperature().getAvgTemperature());
        holder.binding.textViewDate.setText(weather.getDate());
        if (weather.getTemperature().isSnow() == 1) {
            holder.binding.textViewFall.setText(R.string.snow);
        } else if (weather.getTemperature().isRain() == 1) {
            holder.binding.textViewFall.setText(R.string.rain);
        } else {
            holder.binding.textViewFall.setText(R.string.none);
        }
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final WeatherItemBinding binding;

        public WeatherViewHolder(@NonNull WeatherItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

