package com.steelzoo.ownweather.data.weather.model

import androidx.room.Entity

@Entity(tableName = "ultra_short_weather_table", primaryKeys = ["fcstDate", "fcstTime", "category"])
data class UltraShortForecastEntity(
    override val baseDate: String,
    override val baseTime: String,
    override val category: String,
    override val nx: Int,
    override val ny: Int,
    override val fcstDate: String,
    override val fcstTime: String,
    override val fcstValue: String,
) : ForecastWeatherDataModel


@Entity(tableName = "short_weather_table", primaryKeys = ["fcstDate", "fcstTime", "category"])
data class ShortForecastEntity (
    override val baseDate: String,
    override val baseTime: String,
    override val category: String,
    override val nx: Int,
    override val ny: Int,
    override val fcstDate: String,
    override val fcstTime: String,
    override val fcstValue: String,
) : ForecastWeatherDataModel


/**
 * Inheritance to ForecastEntity and DtoItem for Mapper
 */
interface ForecastWeatherDataModel{
    val baseDate: String
    val baseTime: String
    val category: String
    val nx: Int
    val ny: Int
    val fcstDate: String
    val fcstTime: String
    val fcstValue: String
}