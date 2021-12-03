package ge.mormotsadze.weatherapp

import interfaces.CurrentWeatherApi
import interfaces.ServerListener
import models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerData(private var listener: ServerListener?) {

    fun extractData(cityName: String){
        val url = WEATHER_URL

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val weatherApi = retrofit.create(CurrentWeatherApi::class.java)


        val currentWeather = weatherApi.getCurrentWeather(cityName, API_KEY, UNITS)
        val forecastWeather = weatherApi.getForecastWeather(cityName, API_KEY, UNITS)


        currentWeather.enqueue(object : Callback<CurrentWeatherModel> {
            override fun onFailure(call: Call<CurrentWeatherModel>, t: Throwable) {
                listener?.wasFailed(t.localizedMessage)
            }

            override fun onResponse(call: Call<CurrentWeatherModel>, response: Response<CurrentWeatherModel>) {
                if (response.isSuccessful) {
                    val currentWeatherDataModel = CurrentWeatherDataModel(response.body()!!)
                    listener?.wasSuccessful(currentWeatherDataModel)
                } else {
                    listener?.wasNotSuccessful(response.code())
                }
            }
        })

        forecastWeather.enqueue(object : Callback<ForecastWeatherModel> {
            override fun onFailure(call: Call<ForecastWeatherModel>, t: Throwable) {
                listener?.wasFailed(t.localizedMessage)
            }

            override fun onResponse(call: Call<ForecastWeatherModel>, response: Response<ForecastWeatherModel>) {
                if (response.isSuccessful) {
                    val forecastWeatherData = mutableListOf<ForecastWeatherDataModel>()
                    for (i in 0 until NUM_DAYS * NUM_PER_RECORD){
                        forecastWeatherData.add(i, ForecastWeatherDataModel(response.body()!!, i))
                    }

                    listener?.wasSuccessfulForecast(forecastWeatherData)
                } else {
                    listener?.wasNotSuccessful(response.code())
                }
            }
        })
    }
}