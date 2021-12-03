package interfaces

import models.CurrentWeatherDataModel
import models.ForecastWeatherDataModel

interface ServerListener {
    fun wasFailed(errorText: String?)
    fun wasSuccessful(currentWeatherDataModel: CurrentWeatherDataModel)
    fun wasNotSuccessful(errorCode: Int)
    fun wasSuccessfulForecast(forecastWeatherDataModels: List<ForecastWeatherDataModel>)
}