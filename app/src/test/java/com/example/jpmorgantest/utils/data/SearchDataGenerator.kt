package com.example.jpmorgantest.utils.data

import com.example.jpmorgantest.data.sources.search.entities.WeatherDetailResponse
import com.google.gson.Gson

object SearchDataGenerator {
    const val city = "Fairfield"
    const val lat = 41.2668
    const val long = -73.3162
    const val querySearch = "Fairfield,US"
    const val jsonWeatherString = """
        {
   "coord":{
      "lon":-73.3162,
      "lat":41.2668
   },
   "weather":[
      {
         "id":800,
         "main":"Clear",
         "description":"clear sky",
         "icon":"01d"
      }
   ],
   "base":"stations",
   "main":{
      "temp":298.43,
      "feels_like":298.68,
      "temp_min":295.2,
      "temp_max":301.03,
      "pressure":1019,
      "humidity":64
   },
   "visibility":10000,
   "wind":{
      "speed":2.58,
      "deg":323,
      "gust":3.72
   },
   "clouds":{
      "all":2
   },
   "dt":1696356042,
   "sys":{
      "type":2,
      "id":2007980,
      "country":"US",
      "sunrise":1696330297,
      "sunset":1696372375
   },
   "timezone":-14400,
   "id":4834162,
   "name":"Fairfield",
   "cod":200
}
    """

    val weatherData: WeatherDetailResponse = Gson().fromJson(jsonWeatherString, WeatherDetailResponse::class.java)
}
