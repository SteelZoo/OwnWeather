package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.weather.model.WeatherData
import com.steelzoo.ownweather.di.GoRetrofitClient
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherDataRemoteSource @Inject constructor(
    @GoRetrofitClient val goRetrofit: Retrofit
) {
    suspend fun getNowWeatherData(): WeatherData{
        return goRetrofit.create(WeatherService::class.java).getNowWeatherData(
            "20230220",
            "2000",
            55,
            124
        )
    }
}