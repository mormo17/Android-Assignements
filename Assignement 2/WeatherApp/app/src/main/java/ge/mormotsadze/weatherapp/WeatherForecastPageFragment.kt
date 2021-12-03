package ge.mormotsadze.weatherapp

import adapters.WeatherForecastPageRecyclerViewAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import models.ForecastWeatherDataModel


/**
 * A fragment representing a list of Items.
 */
class WeatherForecastPageFragment(forecastWeatherData: List<ForecastWeatherDataModel>?) : Fragment() {

    private var columnCount = 1
    private var models = getModels(forecastWeatherData)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_forecast_page, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                context?.let {
                    HorizontalDividerItemDecoration
                            .Builder(context)
                            .color(ContextCompat.getColor(context, R.color.white))
                            .build()
                }?.let {
                    this.addItemDecoration(it)
                }

                adapter = WeatherForecastPageRecyclerViewAdapter(models)
            }
        }
        return view
    }

    private fun getModels(forecastWeatherData: List<ForecastWeatherDataModel>?):List<ForecastWeatherDataModel>{
        val models = mutableListOf<ForecastWeatherDataModel>()
        val size = forecastWeatherData?.size
        if (size != null){
            for (i in 0 until size){
                models.add(i, forecastWeatherData[i])
            }
        }
        return models
    }
}