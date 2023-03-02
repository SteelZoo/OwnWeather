package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.toNowWeatherData
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeatherData(): NowWeatherData? {
        //TODO
        val weatherData = remoteSource.getNowWeatherData()
        if (weatherData.response.body == null) {
            return null
        }
        return weatherData.toNowWeatherData()
    }
}