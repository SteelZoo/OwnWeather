package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.toNowCastData
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.domain.model.data.NowCastData
import com.steelzoo.ownweather.domain.model.data.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {

    override suspend fun getNowWeatherData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): NowCastData {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        return remoteSource.getNowWeatherData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ).toNowCastData()
    }

    override suspend fun getUltraShortForecastData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): List<ShortForecastData> {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        return remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ).toShortForecastDataList()
    }

    override suspend fun getShortForecastData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): List<ShortForecastData> {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        return remoteSource.getShortForecastData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ).toShortForecastDataList()
    }
}