package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.data.NowCastData
import com.steelzoo.ownweather.domain.model.data.ShortForecastData

interface WeatherDataRepository {
    suspend fun getNowWeatherData(lat: Double, lng: Double, currentTime: Long): NowCastData

    suspend fun getUltraShortForecastData(lat: Double, lng: Double, currentTime: Long): List<ShortForecastData>

    suspend fun getShortForecastData(lat: Double, lng: Double, currentTime: Long): List<ShortForecastData>
}