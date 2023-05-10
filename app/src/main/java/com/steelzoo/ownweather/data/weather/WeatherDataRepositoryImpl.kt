package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.model.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeatherData(lat: Double, lng: Double): NowWeatherData? {
        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        Log.d("LOCATION_REQUEST", "getCurrentLocation: $nxnyMap")

        val nowWeatherDto = remoteSource.getNowWeatherData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.NOWCAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.NOWCAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )
        val ultraShortWeatherDto = remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )
        if (nowWeatherDto.response.body == null || ultraShortWeatherDto.response.body == null) {
            return null
        }
        return getNowWeatherDataWithNowAndUltraShort(nowWeatherDto, ultraShortWeatherDto)
    }

    override suspend fun getShortForecast(lat: Double, lng: Double): List<ShortForecastData>? {
        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)

        val ultraShortWeatherDto = remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )

        val shortWeatherDto = remoteSource.getShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.SHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.SHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        )

        if (ultraShortWeatherDto.response.body == null || shortWeatherDto.response.body == null){
            return null
        }


        return ultraShortWeatherDto.toShortForecastDataList().toMutableList().apply {
            if (this.first().time == WeatherUtil.hourDateFormat.format(currentTime)){
                this.removeFirst()
            }
            shortWeatherDto.toShortForecastDataList().toMutableList().forEach { shortForecastData ->
                if ((this.last().date+this.last().time).toLong() < (shortForecastData.date+shortForecastData.time).toLong()){
                    this.add(shortForecastData)
                }
            }
        }

//        return ultraShortWeatherDto.toShortForecastDataList()
//        return shortWeatherDto.toShortForecastDataList()
    }
}