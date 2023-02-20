package com.steelzoo.ownweather.data.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherDataItem(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val obsrValue: Double,
)

data class WeatherDataItemList(
    @SerializedName("item")
    val weatherItemList: List<WeatherDataItem>,
)

data class WeatherDataBody(
    @SerializedName("items")
    val weatherItems: WeatherDataItemList,
)

data class WeatherDataHeader(
    val resultCode: String,
    val resultMsg: String,
)

data class WeatherDataResponse(
    val header: WeatherDataHeader,
    val body: WeatherDataBody,
)

data class WeatherData(
    val response: WeatherDataResponse,
)