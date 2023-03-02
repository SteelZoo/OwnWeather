package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import com.steelzoo.ownweather.di.GoRetrofitClient
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherDataRemoteSource @Inject constructor(
    @GoRetrofitClient val goRetrofit: Retrofit
) {
    suspend fun getNowWeatherData(): NowWeatherDto {
        return goRetrofit.create(WeatherService::class.java).getNowWeather(
            "20230303",
            "0400",
            55,
            124
        )
    }

    suspend fun getUltraShortForecastData(): ForecastWeatherDto{
        return goRetrofit.create(WeatherService::class.java).getUltraShortForecast(
            "20230222",
            "1800",
            55,
            124
        )
    }
}