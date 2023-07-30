package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.toNowCastData
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.domain.model.data.NowCastData
import com.steelzoo.ownweather.domain.model.data.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherDataRemoteSource,
    private val localSource: WeatherDataLocalSource
) : WeatherDataRepository {

    override suspend fun getNowWeatherData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): NowCastData {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)

        val nowWeatherDtoItemList = localSource.getNowWeatherData()

        return if (
            nowWeatherDtoItemList.isNotEmpty() &&
            nowWeatherDtoItemList.first().baseTime == WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST) &&
            nowWeatherDtoItemList.first().nx == nxnyMap["nx"] &&
            nowWeatherDtoItemList.first().ny == nxnyMap["ny"]
        ) {
            nowWeatherDtoItemList.toNowCastData().also {
                Log.d("NOW_DATA", "get from local")
            }
        } else {
            runCatching {
                remoteSource.getNowWeatherData(
                    WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
                    WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
                    nxnyMap["nx"]!!,
                    nxnyMap["ny"]!!
                ).response.body.weatherItems.weatherItemList
            }.let { result ->
                if (result.isFailure) {
                    nowWeatherDtoItemList.toNowCastData().also {
                        Log.d("NOW_DATA", "get from local")
                    }
                } else {
                    result.getOrNull()!!.also {
                        localSource.saveNowWeatherData(it).also {
                            Log.d("NOW_DATA", "save to local from net")
                        }
                    }.toNowCastData().also {
                        Log.d("NOW_DATA", "get from network")
                    }
                }
            }
        }

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
        ).response.body.weatherItems.weatherItemList.toShortForecastDataList().also {
            Log.d("LIST_MAPPER_TEST", it.toString())
        }
    }

    override suspend fun getShortForecastData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): List<ShortForecastData> {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        return remoteSource.getShortForecastData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ).response.body.weatherItems.weatherItemList.toShortForecastDataList().also {
            Log.d("LIST_MAPPER_TEST", it.toString())
        }
    }
}