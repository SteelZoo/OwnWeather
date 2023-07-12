package com.steelzoo.ownweather.domain.model.ui

import com.steelzoo.ownweather.domain.model.SkyState

data class NowWeatherData(
    val baseTime: String,
    val skyState: SkyState,
    val temperature: Double,
    val oneHourPrecipitation: Double,
    val eastWestWindComponent: Double,
    val northSouthWindComponent: Double,
    val humidity: Double,
    val windDirection: Double,
    val windSpeed: Double
)