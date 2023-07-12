package com.steelzoo.ownweather.domain.model.data

data class NowCastData(
    val baseTime: String,
    val temperature: Double,
    val oneHourPrecipitation: Double,
    val eastWestWindComponent: Double,
    val northSouthWindComponent: Double,
    val humidity: Double,
    val windDirection: Double,
    val windSpeed: Double
)

