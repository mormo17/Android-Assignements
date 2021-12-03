package models

import adapters.TimeManager
import ge.mormotsadze.weatherapp.CELSIUS
import ge.mormotsadze.weatherapp.HOURS
import ge.mormotsadze.weatherapp.PERCENT
import kotlin.math.roundToInt

data class CurrentWeatherDataModel(val currentWeatherModel: CurrentWeatherModel) {

    var temperature = currentWeatherModel.main.temp.roundToInt().toString() + CELSIUS
    var desc = currentWeatherModel.weather[0].description
    var iconName = currentWeatherModel.weather[0].icon

    var time = TimeManager.getTimeByFormat(currentWeatherModel.time, HOURS)

    /* Details Data*/
    var feelsLike = currentWeatherModel.main.feels_like.roundToInt().toString() + CELSIUS
    var humidity = currentWeatherModel.main.humidity.toString() + PERCENT
    var pressure = currentWeatherModel.main.pressure.toString()
}