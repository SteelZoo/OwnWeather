package com.steelzoo.ownweather.domain.repositoryinterface

import com.steelzoo.ownweather.domain.model.NowWeatherData

interface WeatherDataRepository {
    suspend fun getNowWeatherData(lat: Double, lng: Double): NowWeatherData?
}