package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.WeatherData

interface WeatherDataRepository {
    fun getNowWeather(): WeatherData
}