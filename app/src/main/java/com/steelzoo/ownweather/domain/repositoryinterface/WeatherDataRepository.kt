package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.NowWeatherData

interface WeatherDataRepository {
    suspend fun getNowWeather(): NowWeatherData?
}