package com.steelzoo.ownweather.domain.model

data class WeatherData(
    val temperature: Double,
    val oneHourPrecipitation: Double,
    val eastWestWindComponent: Double,
    val northSouthWindComponent: Double,
    val humidity: Double,
    val precipitationType: Int,
    val windDirection: Double,
    val windSpeed: Double
)

