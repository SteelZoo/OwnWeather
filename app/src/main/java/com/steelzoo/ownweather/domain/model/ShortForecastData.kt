package com.steelzoo.ownweather.domain.model

data class ShortForecastData (
    val date: String,
    val time: String,
    val skyState: SkyState,
    val temperature: Int,
    val humidity: Int,
    val precipitation: String,
    val probability: Int,
)