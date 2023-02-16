package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.domain.model.WeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeather(): String {
        return remoteSource.getNowWeatherData()
    }
}