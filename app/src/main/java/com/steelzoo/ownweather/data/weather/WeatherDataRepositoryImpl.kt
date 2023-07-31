package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.toNowCastData
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDataModel
import com.steelzoo.ownweather.data.weather.model.NowWeatherDtoItem
import com.steelzoo.ownweather.data.weather.model.ShortForecastEntity
import com.steelzoo.ownweather.data.weather.model.UltraShortForecastEntity
import com.steelzoo.ownweather.domain.model.data.NowCastData
import com.steelzoo.ownweather.domain.model.data.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

//TODO 로컬, 네트워크 관련 로직 간소화 하기
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
            nowWeatherDtoItemList.isNotEmpty()
            && nowWeatherDtoItemList.first().baseTime == WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST)
            && nowWeatherDtoItemList.first().nx == nxnyMap["nx"]
            && nowWeatherDtoItemList.first().ny == nxnyMap["ny"]
        ) {
            nowWeatherDtoItemList.toNowCastData()
                .also { Log.d("CHECK_CACHING", "now get from local") }
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
                    nowWeatherDtoItemList.toNowCastData()
                        .also { Log.d("CHECK_CACHING", "now get from local") }
                } else {
                    result.getOrNull()!!.also {
                        localSource.saveNowWeatherData(it)
                            .also { Log.d("CHECK_CACHING", "now save to local from net") }
                    }.toNowCastData()
                        .also { Log.d("CHECK_CACHING", "now get from network") }
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

        val ultraShortForecastEntityList = localSource.getUltraShortForecastData()

        return if (
            ultraShortForecastEntityList.isNotEmpty()
            && ultraShortForecastEntityList.first().baseTime.replace("30","00") == WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST)
            && ultraShortForecastEntityList.first().nx == nxnyMap["nx"]
            && ultraShortForecastEntityList.first().ny == nxnyMap["ny"]
        ) {
            ultraShortForecastEntityList.toShortForecastDataList()
                .also { Log.d("CHECK_CACHING", "ultra get from local") }
        } else {
            runCatching {
                remoteSource.getUltraShortForecastData(
                    WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                    WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                    nxnyMap["nx"]!!,
                    nxnyMap["ny"]!!
                ).response.body.weatherItems.weatherItemList.map {
                    UltraShortForecastEntity(
                            it.baseDate,
                            it.baseTime,
                            it.category,
                            it.nx,
                            it.ny,
                            it.fcstDate,
                            it.fcstTime,
                            it.fcstValue
                    )
                }
            }.let { result ->
                if (result.isFailure) {
                    ultraShortForecastEntityList.toShortForecastDataList()
                        .also { Log.d("CHECK_CACHING", "ultra get from local") }
                } else {
                    result.getOrNull()!!.also {
                        localSource.saveUltraShortForecastData(it)
                            .also { Log.d("CHECK_CACHING", "ultra save to local from net") }
                    }.toShortForecastDataList()
                        .also { Log.d("CHECK_CACHING", "ultra get from network") }
                }
            }
        }
//        return .toShortForecastDataList()
    }

    override suspend fun getShortForecastData(
        lat: Double,
        lng: Double,
        currentTime: Long
    ): List<ShortForecastData> {
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)

        val shortForecastEntityList = localSource.getShortForecastData()

        return if (
            shortForecastEntityList.isNotEmpty()
            && shortForecastEntityList.first().baseTime == WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST)
            && shortForecastEntityList.first().nx == nxnyMap["nx"]
            && shortForecastEntityList.first().ny == nxnyMap["ny"]
        ) {
            shortForecastEntityList.toShortForecastDataList()
                .also { Log.d("CHECK_CACHING", "short get from local") }

        }else {
            runCatching {
                remoteSource.getShortForecastData(
                    WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
                    WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
                    nxnyMap["nx"]!!,
                    nxnyMap["ny"]!!
                ).response.body.weatherItems.weatherItemList.map {
                    ShortForecastEntity(
                        it.baseDate,
                        it.baseTime,
                        it.category,
                        it.nx,
                        it.ny,
                        it.fcstDate,
                        it.fcstTime,
                        it.fcstValue
                    )
                }
            }.let { result ->
                if (result.isFailure) {
                    shortForecastEntityList.toShortForecastDataList()
                        .also { Log.d("CHECK_CACHING", "short get from local") }
                } else {
                    result.getOrNull()!!.also {
                        localSource.saveShortForecastData(it)
                            .also { Log.d("CHECK_CACHING", "short save to local from net") }
                    }.toShortForecastDataList()
                        .also { Log.d("CHECK_CACHING", "short get from network") }
                }
            }
        }

    }
}