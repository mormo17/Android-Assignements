package models

import com.google.gson.annotations.SerializedName

data class ForecastWeatherModel(var list: List<ForecastList>)

data class ForecastList(var main: Main,
                        var weather: List<Weather>,
                        @SerializedName("dt")
                        var date: Long)

