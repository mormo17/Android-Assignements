package models

import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel (val main: Main,
                                val name: String,
                                val weather: List<Weather>,
                                @SerializedName("dt")
                                val time: Long)

data class Main(val temp: Float, val feels_like: Float, val humidity: Int, var pressure: Int)

data class Weather(val description: String, val icon: String)

