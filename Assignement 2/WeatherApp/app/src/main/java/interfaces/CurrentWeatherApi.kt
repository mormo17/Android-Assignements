package interfaces

import models.CurrentWeatherModel
import models.ForecastWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrentWeatherApi {

    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String,
                          @Query("appid") apiKey: String,
                          @Query("units") units: String): Call<CurrentWeatherModel>

    @GET("forecast")
    fun getForecastWeather(@Query("q") cityName: String,
                           @Query("appid") apiKey: String,
                           @Query("units") units: String): Call<ForecastWeatherModel>

}