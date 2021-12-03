package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.mormotsadze.weatherapp.CurrentWeatherPage
import ge.mormotsadze.weatherapp.WeatherForecastPageFragment
import models.CurrentWeatherDataModel
import models.ForecastWeatherDataModel


class ViewPagerViewAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    currentWeatherData: CurrentWeatherDataModel?,
    forecastWeatherData: List<ForecastWeatherDataModel>?
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private var fragments: ArrayList<Fragment> = arrayListOf(CurrentWeatherPage(currentWeatherData),
                                        WeatherForecastPageFragment(forecastWeatherData))

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
