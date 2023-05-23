package com.steelzoo.ownweather.domain.usecase

import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.data.weather.WeatherUtil
import com.steelzoo.ownweather.domain.model.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetShortForecastUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): List<ShortForecastData>?{
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val currentTime = System.currentTimeMillis()
        val nxnyMap = WeatherUtil.convertLatLngToGridXY(lat, lng)

        val ultraShortWeatherCall = coroutineScope.async { weatherDataRepository.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.ULTRASHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ) }

        val shortWeatherCall = coroutineScope.async { weatherDataRepository.getShortForecastData(
            WeatherUtil.getBaseDate(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
            WeatherUtil.getBaseTime(currentTime, WeatherUtil.BaseTimeType.SHORT_FORECAST),
            nxnyMap["nx"]!!,
            nxnyMap["ny"]!!
        ) }

        val ultraShortWeatherDto = ultraShortWeatherCall.await()
        val shortWeatherDto = shortWeatherCall.await()


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
    }
}