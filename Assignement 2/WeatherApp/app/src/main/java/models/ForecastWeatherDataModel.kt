package models

import adapters.TimeManager
import ge.mormotsadze.weatherapp.CELSIUS
import ge.mormotsadze.weatherapp.DATE
import kotlin.math.roundToInt

class ForecastWeatherDataModel(forecastWeatherModel: ForecastWeatherModel, indx: Int) {
    var temperature = forecastWeatherModel.list[indx].main.temp.roundToInt().toString() + CELSIUS
    var desc = forecastWeatherModel.list[indx].weather[0].description
    var iconName = forecastWeatherModel.list[indx].weather[0].icon

    private var initialDate = forecastWeatherModel.list[indx].date
    var date = TimeManager.getTimeByFormat(initialDate, DATE)
}
