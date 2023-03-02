package com.steelzoo.ownweather.ui.model

data class WeatherDataUI(
    val skyType: SkyType,
    val temperature: Double,
    val humidity: Int,
    val windDirection: Double,
    val windSpeed: Double,
    val fineDust: Int,
    val ultraFineDust: Int,
)