package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeatherData(lat: Double, lng: Double): NowWeatherData? {
        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        Log.d("LOCATION_REQUEST", "getCurrentLocation: $nxnyMap")

        val nowWeatherDto = remoteSource.getNowWeatherData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )
        val ultraShortWeatherDto = remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseMinuteType.ULTRASHORT_FORECAST_BASEMINUTE),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseMinuteType.ULTRASHORT_FORECAST_BASEMINUTE),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )
        if (nowWeatherDto.response.body == null || ultraShortWeatherDto.response.body == null) {
            return null
        }
        return getNowWeatherDataWithNowAndUltraShort(nowWeatherDto, ultraShortWeatherDto)
    }
}