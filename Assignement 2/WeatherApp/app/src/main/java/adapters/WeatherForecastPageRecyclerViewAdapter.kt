package adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ge.mormotsadze.weatherapp.R
import ge.mormotsadze.weatherapp.WEATHER_ICON_FORMAT
import ge.mormotsadze.weatherapp.WEATHER_ICON_URL
import models.ForecastWeatherDataModel


class WeatherForecastPageRecyclerViewAdapter(
    private val values: List<ForecastWeatherDataModel>
) : RecyclerView.Adapter<WeatherForecastPageRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_weather_forecast_page_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.dateLabel.text = item.date
        holder.temperatureLabel.text = item.temperature
        holder.weatherDescLabel.text = item.desc
        Glide.with(holder.view)
            .load(WEATHER_ICON_URL + item.iconName + WEATHER_ICON_FORMAT)
            .circleCrop()
            .into(holder.forecastItemIcon)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val dateLabel: TextView = view.findViewById(R.id.second_page_item_date)
        val temperatureLabel: TextView = view.findViewById(R.id.second_page_item_temperature)
        val weatherDescLabel: TextView = view.findViewById(R.id.second_page_item_weather_desc)
        val forecastItemIcon: ImageView = view.findViewById(R.id.second_page_item_weather_icon)
        override fun toString(): String {
            return super.toString() + " '" + dateLabel.text + " " + temperatureLabel.text + " " + weatherDescLabel.text + "'"
        }
    }
}