package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.model.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeatherData(lat: Double, lng: Double): NowWeatherData? {
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        Log.d("LOCATION_REQUEST", "getCurrentLocation: $nxnyMap")

        val nowWeatherDto = coroutineScope.async {
            remoteSource.getNowWeatherData(
                WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.NOWCAST),
                WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.NOWCAST),
                nxnyMap["nx"]!!,
                nxnyMap["ny"]!!
            )
        }

        val ultraShortWeatherDto = coroutineScope.async {
            remoteSource.getUltraShortForecastData(
                WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                nxnyMap["nx"]!!,
                nxnyMap["ny"]!!
            )
        }

        if (nowWeatherDto.await().response.body == null || ultraShortWeatherDto.await().response.body == null) {
            return null
        }

        return getNowWeatherDataWithNowAndUltraShort(nowWeatherDto.await(), ultraShortWeatherDto.await())
    }

    override suspend fun getShortForecast(lat: Double, lng: Double): List<ShortForecastData>? {
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)

        val ultraShortWeatherDto = coroutineScope.async { remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ) }

        val shortWeatherDto = coroutineScope.async { remoteSource.getShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseTimeType.SHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseTimeType.SHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ) }

        Log.d("FORECAST", "getShortForecast: $ultraShortWeatherDto/n $shortWeatherDto")

        if (ultraShortWeatherDto.await().response.body == null || shortWeatherDto.await().response.body == null){
            return null
        }

        return ultraShortWeatherDto.await().toShortForecastDataList().toMutableList().apply {
            if (this.first().time == WeatherUtil.hourDateFormat.format(currentTime)){
                this.removeFirst()
            }
            shortWeatherDto.await().toShortForecastDataList().toMutableList().forEach { shortForecastData ->
                if ((this.last().date+this.last().time).toLong() < (shortForecastData.date+shortForecastData.time).toLong()){
                    this.add(shortForecastData)
                }
            }
        }

//        return ultraShortWeatherDto.toShortForecastDataList()
//        return shortWeatherDto.toShortForecastDataList()
    }
}