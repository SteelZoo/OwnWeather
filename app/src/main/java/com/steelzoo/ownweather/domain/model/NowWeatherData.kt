package com.steelzoo.ownweather.domain.model

data class NowWeatherData(
    val skyState: SkyState,
    val temperature: Double,
    val oneHourPrecipitation: Double,
    val eastWestWindComponent: Double,
    val northSouthWindComponent: Double,
    val humidity: Double,
    val windDirection: Double,
    val windSpeed: Double
)

