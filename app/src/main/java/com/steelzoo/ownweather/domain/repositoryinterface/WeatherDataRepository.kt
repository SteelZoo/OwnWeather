package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.model.ShortForecastData

interface WeatherDataRepository {
    suspend fun getNowWeatherData(lat: Double, lng: Double): NowWeatherData?

    suspend fun getShortForecast(lat: Double, lng: Double): List<ShortForecastData>?
}