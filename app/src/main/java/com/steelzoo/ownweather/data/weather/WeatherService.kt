package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.BuildConfig
import com.steelzoo.ownweather.data.WEATHER_NOW_URL
import com.steelzoo.ownweather.data.WEATHER_SHORT_URL
import com.steelzoo.ownweather.data.WEATHER_ULTRASHORT_URL
import com.steelzoo.ownweather.data.WEATHER_URL
import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("$WEATHER_URL$WEATHER_NOW_URL?serviceKey=${BuildConfig.GO_API_KEY}&dataType=JSON&numOfRows=100&pageNo=1")
    suspend fun getNowWeather(
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
    ): NowWeatherDto

    @GET("$WEATHER_URL$WEATHER_ULTRASHORT_URL?serviceKey=${BuildConfig.GO_API_KEY}&dataType=JSON&numOfRows=100&pageNo=1")
    suspend fun getUltraShortForecast(
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
    ): ForecastWeatherDto

    @GET("$WEATHER_URL$WEATHER_SHORT_URL?serviceKey=${BuildConfig.GO_API_KEY}&dataType=JSON&numOfRows=1000&pageNo=1")
    suspend fun getShortForecast(
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
    ): ForecastWeatherDto
}