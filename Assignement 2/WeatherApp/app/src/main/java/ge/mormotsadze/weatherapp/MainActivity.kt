package ge.mormotsadze.weatherapp

import adapters.ViewPagerViewAdapter
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import interfaces.ServerListener
import models.CurrentWeatherDataModel
import models.ForecastWeatherDataModel


class MainActivity : AppCompatActivity(), ServerListener {

    /* ImageViews */
    private lateinit var flagIconGeorgia: ImageView
    private lateinit var flagIconUK: ImageView
    private lateinit var flagIconJamaica: ImageView

    /* TextViews */
    private lateinit var cityName: TextView

    private lateinit var viewPager: ViewPager2
    private lateinit var mainView: ConstraintLayout
    private var cachedCurrentWeatherData: MutableMap<String, CurrentWeatherDataModel> = mutableMapOf()
    private var cachedForecastWeatherData: MutableMap<String, List<ForecastWeatherDataModel>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFlagIcons()
        initViewPager()
        extractData()
        mainView = findViewById(R.id.main_view)
    }

    private fun extractData() {
        var wasCurrentCashed = false
        var wasForecastCashed = false

        /* Cache Current Weather Information */
        if(cachedCurrentWeatherData.containsKey(cityName.text)){
            wasSuccessful(cachedCurrentWeatherData[cityName.text as String]!!)
            wasCurrentCashed = true
            Log.i("Test Caching", "Info was already cashed so lets extract from cachedData (Current Weather) " + cityName.text)
        }

        /* Cache Forecast Weather Information */
        if(cachedForecastWeatherData.containsKey(cityName.text)){
            wasSuccessfulForecast(cachedForecastWeatherData[cityName.text as String]!!)
            wasForecastCashed = true
            Log.i("Test Caching", "Info was already cashed so lets extract from cachedData (Forecast Weather) " + cityName.text)
        }

        /* Only get Information from server, if one of them were not cached */
        if (!wasCurrentCashed or !wasForecastCashed){
            Log.i("Test Caching", "Info was not yet cashed so lets extract from server " + cityName.text)
            val serverData = ServerData(this)
            serverData.extractData(cityName.text as String)
        }
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.view_pager)
    }

    private fun initTabLayout(){
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)

        val icons = arrayListOf(
            R.drawable.today,
            R.drawable.hourly
        )

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.icon = ResourcesCompat.getDrawable(resources, icons[position], null)
            val tintColor = ContextCompat.getColor(applicationContext, R.color.white)
            tab.icon?.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN)
        }.attach()

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    private fun initFlagIcons(){
        initGeorgiaFlagIcon()
        initUKFlagIcon()
        initJamaicaFlagIcon()
        cityName = findViewById(R.id.city_name)
    }

    private fun initGeorgiaFlagIcon(){
        flagIconGeorgia = findViewById(R.id.flag_icon_georgia)
        flagIconGeorgia.setOnClickListener {
            cityName.text = getCapital(GEORGIA)
            Log.i("Flag", "Georgia Flag Pressed!")
            extractData()
        }
    }

    private fun initUKFlagIcon(){
        flagIconUK = findViewById(R.id.flag_icon_uk)
        flagIconUK.setOnClickListener {
            cityName.text = getCapital(UK)
            Log.i("Flag", "UK Flag Pressed!")
            extractData()
        }
    }

    private fun initJamaicaFlagIcon(){
        flagIconJamaica = findViewById(R.id.flag_icon_jamaica)
        flagIconJamaica.setOnClickListener {
            cityName.text = getCapital(JAMAICA)
            Log.i("Flag", "Jamaica Flag Pressed!")
            extractData()
        }
    }

    private fun getCapital(country: String): String{
        return when (country) {
            GEORGIA -> TBILISI
            UK -> LONDON
            JAMAICA -> KINGSTON
            else -> ""
        }
    }

    private fun getTimePeriod(time: String): String{
        if(time.toInt() in 6..18) return AM
        return PM
    }

    override fun wasSuccessful(currentWeatherDataModel: CurrentWeatherDataModel) {
        if (!cachedCurrentWeatherData.containsKey(cityName.text)){
            cachedCurrentWeatherData[cityName.text as String] = currentWeatherDataModel
            Log.i("Test Caching", "Info was not yet cashed so lets extract from server and save it (Current Weather)")
        }

        val adapter = ViewPagerViewAdapter(
            supportFragmentManager,
            lifecycle, currentWeatherDataModel,
            cachedForecastWeatherData[cityName.text]
        )

        if (getTimePeriod(currentWeatherDataModel.time) == AM){
            mainView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.background_day))
        } else{
            mainView.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.background_night))
        }
        viewPager.adapter = adapter

        initTabLayout()
    }

    override fun wasSuccessfulForecast(forecastWeatherDataModels: List<ForecastWeatherDataModel>) {
        if (!cachedForecastWeatherData.containsKey(cityName.text)){
            cachedForecastWeatherData[cityName.text as String] = forecastWeatherDataModels
            Log.i("Test Caching", "Info was not yet cashed so lets extract from server and save it (Forecast Weather)")
        }

        val adapter = ViewPagerViewAdapter(
                supportFragmentManager,
                lifecycle,
                cachedCurrentWeatherData[cityName.text],
                forecastWeatherDataModels)

        viewPager.adapter = adapter

        initTabLayout()
    }

    override fun wasNotSuccessful(errorCode: Int) {
        Toast.makeText(this, "$errorCode Error Code", Toast.LENGTH_LONG).show()
    }

    override fun wasFailed(errorText: String?) {
        Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
    }

}