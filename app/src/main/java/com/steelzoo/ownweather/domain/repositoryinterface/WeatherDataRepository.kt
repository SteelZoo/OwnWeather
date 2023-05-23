package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.model.ShortForecastData

interface WeatherDataRepository {
    suspend fun getNowWeatherData(baseDate: String, baseTime: String, nx: Int, ny: Int): NowWeatherDto

    suspend fun getUltraShortForecastData(baseDate: String, baseTime: String, nx: Int, ny: Int): ForecastWeatherDto

    suspend fun getShortForecastData(baseDate: String, baseTime: String, nx: Int, ny: Int): ForecastWeatherDto
}