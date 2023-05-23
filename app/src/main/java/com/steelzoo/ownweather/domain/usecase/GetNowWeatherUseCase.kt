package com.steelzoo.ownweather.domain.usecase

import android.util.Log
import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.data.weather.WeatherUtil
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetNowWeatherUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): NowWeatherData?{
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)
        Log.d("LOCATION_REQUEST", "getCurrentLocation: $nxnyMap")

        val nowWeatherCall = coroutineScope.async {
            weatherDataRepository.getNowWeatherData(
                WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
                WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.NOWCAST),
                nxnyMap["nx"]!!,
                nxnyMap["ny"]!!
            )
        }

        val ultraShortWeatherCall = coroutineScope.async {
            weatherDataRepository.getUltraShortForecastData(
                WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
                nxnyMap["nx"]!!,
                nxnyMap["ny"]!!
            )
        }

        val nowWeatherDto = nowWeatherCall.await()
        val ultraShortWeatherDto = ultraShortWeatherCall.await()

        if (nowWeatherDto.response.body == null || ultraShortWeatherDto.response.body == null) {
            return null
        }

        return getNowWeatherDataWithNowAndUltraShort(nowWeatherDto, ultraShortWeatherDto)
    }
}