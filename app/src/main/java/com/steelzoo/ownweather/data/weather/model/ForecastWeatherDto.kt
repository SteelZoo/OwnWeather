package com.steelzoo.ownweather.data.weather.model

import com.google.gson.annotations.SerializedName

data class ForecastWeatherDtoItem(
    override val baseDate: String,
    override val baseTime: String,
    override val category: String,
    override val nx: Int,
    override val ny: Int,
    override val fcstDate: String,
    override val fcstTime: String,
    override val fcstValue: String,
) : ForecastWeatherDataModel

data class ForecastWeatherDtoItemList(
    @SerializedName("item")
    val weatherItemList: List<ForecastWeatherDtoItem>,
)

data class ForecastWeatherDtoBody(
    @SerializedName("items")
    val weatherItems: ForecastWeatherDtoItemList,
)

data class ForecastWeatherDtoResponse(
    val header: WeatherDtoHeader,
    val body: ForecastWeatherDtoBody,
)

data class ForecastWeatherDto(
    val response: ForecastWeatherDtoResponse,
)