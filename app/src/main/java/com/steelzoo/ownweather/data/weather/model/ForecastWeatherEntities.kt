package com.steelzoo.ownweather.data.weather.model

import androidx.room.Entity

@Entity(tableName = "ultra_short_weather_table", primaryKeys = ["baseDate", "baseTime", "category"])
data class UltraShortForecastEntity(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
)


@Entity(tableName = "short_weather_table", primaryKeys = ["baseDate", "baseTime", "category"])
data class ShortForecastEntity(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    val nx: Int,
    val ny: Int,
    val fcstDate: String,
    val fcstTime: String,
    val fcstValue: String,
)