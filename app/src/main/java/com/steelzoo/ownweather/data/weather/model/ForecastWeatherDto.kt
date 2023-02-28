package com.steelzoo.ownweather.data.weather.model

import com.google.gson.annotations.SerializedName

data class ForecastWeatherDtoItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
)

data class ForecastWeatherDtoItemList(
    @SerializedName("item")
    val weatherItemList: List<NowWeatherDtoItem>,
)

data class ForecastWeatherDtoBody(
    @SerializedName("items")
    val weatherItems: NowWeatherDtoItemList,
)

data class ForecastWeatherDtoResponse(
    val header: WeatherDtoHeader,
    val body: NowWeatherDtoBody,
)

data class ForecastWeatherDto(
    val response: NowWeatherDtoResponse,
)