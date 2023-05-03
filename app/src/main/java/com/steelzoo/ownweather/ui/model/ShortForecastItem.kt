package com.steelzoo.ownweather.ui.model

data class ShortForecastItem (
    val time: String,
    val skyState: SkyStateUI,
    val temperature: Int,
    val humidity: Int,
    val precipitation: Double,
    val probability: Int,
)