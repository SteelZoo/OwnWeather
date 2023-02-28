package com.steelzoo.ownweather.data.weather.model

import com.google.gson.annotations.SerializedName

data class NowWeatherDtoItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue: Double,
)

data class NowWeatherDtoItemList(
    @SerializedName("item")
    val weatherItemList: List<NowWeatherDtoItem>,
)

data class NowWeatherDtoBody(
    @SerializedName("items")
    val weatherItems: NowWeatherDtoItemList,
)

data class NowWeatherDtoResponse(
    val header: WeatherDtoHeader,
    val body: NowWeatherDtoBody,
)

data class NowWeatherDto(
    val response: NowWeatherDtoResponse,
)