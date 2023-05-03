package com.steelzoo.ownweather.domain.model

data class ShortForecastData (
    val time: String,
    val skyState: SkyState,
    val temperature: Int,
    val humidity: Int,
    val precipitation: Double,
    val probability: Int,
)