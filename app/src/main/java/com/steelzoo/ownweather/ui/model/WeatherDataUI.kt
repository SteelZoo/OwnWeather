package com.steelzoo.ownweather.ui.model

data class WeatherDataUI(
    val skyState: SkyState,
    val temperature: Double,
    val humidity: Int,
    val windDirection: Double,
    val windSpeed: Double,
    val fineDust: Int,
    val ultraFineDust: Int,
)