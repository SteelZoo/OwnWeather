package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import com.steelzoo.ownweather.di.GoRetrofitClient
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherDataRemoteSource @Inject constructor(
    @GoRetrofitClient val goRetrofit: Retrofit
) {
    suspend fun getNowWeatherData(baseDate: String, baseTime: String, nx: Int, ny: Int): NowWeatherDto {
        return goRetrofit.create(WeatherService::class.java).getNowWeather(
            baseDate, baseTime, nx, ny
        )
    }

    suspend fun getUltraShortForecastData(baseDate: String, baseTime: String, nx: Int, ny: Int): ForecastWeatherDto{
        return goRetrofit.create(WeatherService::class.java).getUltraShortForecast(
            baseDate, baseTime, nx, ny
        )
    }

    suspend fun getShortForecastData(baseDate: String, baseTime: String, nx: Int, ny: Int): ForecastWeatherDto{
        return goRetrofit.create(WeatherService::class.java).getShortForecast(
            baseDate, baseTime, nx, ny
        )
    }
}