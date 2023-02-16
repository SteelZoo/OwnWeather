package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.BuildConfig
import com.steelzoo.ownweather.data.WEATHER_NOW_URL
import com.steelzoo.ownweather.data.WEATHER_URL
import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("$WEATHER_URL$WEATHER_NOW_URL?serviceKey=${BuildConfig.GO_API_KEY}&dataType=JSON&numOfRows=100&pageNo=1&base_date=20230216&base_time=2200&nx=55&ny=124")
    suspend fun getNowWeatherData(): String
}