package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.data.weather.model.WeatherData

interface WeatherDataRepository {
    suspend fun getNowWeather(): WeatherData
}