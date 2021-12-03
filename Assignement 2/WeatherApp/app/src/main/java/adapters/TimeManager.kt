package adapters

import ge.mormotsadze.weatherapp.MILLISECONDS
import java.text.SimpleDateFormat
import java.util.*


object TimeManager {

    fun getTimeByFormat(time: Long, format: String): String{
        val date = Date(time * MILLISECONDS)
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

}