package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.WeatherData

interface WeatherDataRepository {
    suspend fun getNowWeather(): String
}