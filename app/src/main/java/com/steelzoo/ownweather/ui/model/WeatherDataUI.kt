package com.steelzoo.ownweather.ui.model

data class WeatherDataUI(
    val baseTime: String,
    val skyState: SkyStateUI,
    val temperature: Double,
    val humidity: Int,
    val rain: Double,
    val windDirection: Double,
    val windSpeed: Double,
    val fineDust: Int,
    val ultraFineDust: Int,
)