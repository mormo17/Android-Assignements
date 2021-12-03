package ge.mormotsadze.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import models.CurrentWeatherDataModel


class CurrentWeatherPage(private var currentWeatherDataModel: CurrentWeatherDataModel?) : Fragment() {
    private var currentWeatherPage: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currentWeatherPage = inflater.inflate(R.layout.current_weather_page, container, false)
        setUpView()
        return currentWeatherPage
    }

    private fun setUpView(){
        setUpIcon()
        setTemperature()
        setWeatherDesc()
        setHumidity()
        setFeelsLike()
        setPressure()
    }

    private fun setTemperature(){
        val temperatureToSet = currentWeatherDataModel?.temperature

        val temperatureTextView = currentWeatherPage?.findViewById<TextView>(R.id.temperature_label)
        temperatureTextView?.text = temperatureToSet

        val detailsTemperatureTextView = currentWeatherPage?.findViewById<TextView>(R.id.details_temperature_value)
        detailsTemperatureTextView?.text = temperatureToSet
    }

    private fun setWeatherDesc() {
        val weatherDescTextView = currentWeatherPage?.findViewById<TextView>(R.id.weather_desc)
        weatherDescTextView?.text = currentWeatherDataModel?.desc
    }

    private fun setHumidity() {
        val humidityTextView = currentWeatherPage?.findViewById<TextView>(R.id.details_humidity_value)
        humidityTextView?.text = currentWeatherDataModel?.humidity
    }

    private fun setFeelsLike(){
        val detailsFeelsLike = currentWeatherPage?.findViewById<TextView>(R.id.details_feels_like_value)
        detailsFeelsLike?.text = currentWeatherDataModel?.feelsLike
    }

    private fun setPressure(){
        val detailsPressure = currentWeatherPage?.findViewById<TextView>(R.id.details_pressure_value)
        detailsPressure?.text = currentWeatherDataModel?.pressure
    }

    private fun setUpIcon(){
        Glide.with(this)
            .load(WEATHER_ICON_URL + currentWeatherDataModel?.iconName + WEATHER_ICON_FORMAT)
            .circleCrop()
            .into(currentWeatherPage?.findViewById(R.id.weather_icon)!!)
    }

}