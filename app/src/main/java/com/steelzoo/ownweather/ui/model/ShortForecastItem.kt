package com.steelzoo.ownweather.ui.model

data class ShortForecastItem (
    val date: String,
    val time: String,
    val skyState: SkyStateUI,
    val temperature: Int,
    val humidity: Int,
    val precipitation: String,
    val probability: Int,
)