package com.steelzoo.ownweather.domain.usecase

import com.steelzoo.ownweather.data.weather.WeatherUtil
import com.steelzoo.ownweather.domain.model.data.ShortForecastData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetShortForecastUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double, currentTime: Long): List<ShortForecastData>?{
        //TODO Dispatcher 주입
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        val ultraShortWeatherCall = coroutineScope.async {
            weatherDataRepository.getUltraShortForecastData(
                lat, lng, currentTime
            )
        }

        val shortWeatherCall = coroutineScope.async { weatherDataRepository.getShortForecastData(
            lat, lng, currentTime
        ) }

        val ultraShortWeatherDataList = ultraShortWeatherCall.await()
        val shortWeatherDataList = shortWeatherCall.await()

        return ultraShortWeatherDataList.toMutableList().apply {
            if (this.first().time == WeatherUtil.hourDateFormat.format(currentTime)){
                this.removeFirst()
            }
            shortWeatherDataList.toMutableList().forEach { shortForecastData ->
                if ((this.last().date+this.last().time).toLong() < (shortForecastData.date+shortForecastData.time).toLong()){
                    this.add(shortForecastData)
                }
            }
        }
    }
}