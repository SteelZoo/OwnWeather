package com.steelzoo.ownweather.domain.usecase

import com.steelzoo.ownweather.domain.model.ui.NowWeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetNowWeatherUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double, currentTime: Long): NowWeatherData?{
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val nowCastCall = coroutineScope.async {
            weatherDataRepository.getNowWeatherData(
                lat, lng, currentTime
            )
        }

        val ultraShortWeatherCall = coroutineScope.async {
            weatherDataRepository.getUltraShortForecastData(
                lat, lng, currentTime
            )
        }

        //TODO 데이터변환
        val nowCastData = nowCastCall.await()
        val ultraShortWeatherDataList = ultraShortWeatherCall.await()

        return NowWeatherData(
            nowCastData.baseTime,
            ultraShortWeatherDataList.first().skyState,
            nowCastData.temperature,
            nowCastData.oneHourPrecipitation,
            nowCastData.eastWestWindComponent,
            nowCastData.northSouthWindComponent,
            nowCastData.humidity,
            nowCastData.windDirection,
            nowCastData.windSpeed
        )
    }
}