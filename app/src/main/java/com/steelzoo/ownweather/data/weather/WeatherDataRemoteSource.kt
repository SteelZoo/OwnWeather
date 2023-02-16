package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.di.GoRetrofitClient
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherDataRemoteSource @Inject constructor(
    @GoRetrofitClient val goRetrofit: Retrofit
) {
    suspend fun getNowWeatherData(): String{
        return goRetrofit.create(WeatherService::class.java).getNowWeatherData()
    }
}