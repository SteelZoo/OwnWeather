package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.domain.model.NowWeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    val remoteSource: WeatherDataRemoteSource
) : WeatherDataRepository {
    override suspend fun getNowWeatherData(): NowWeatherData? {
        val currentTime = System.currentTimeMillis()
        val nowWeatherDto = remoteSource.getNowWeatherData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE),
            55,
            124
        )
        val ultraShortWeatherDto = remoteSource.getUltraShortForecastData(
            WeatherUtil.getBaseDate(currentTime,WeatherUtil.BaseMinuteType.ULTRASHORT_FORECAST_BASEMINUTE),
            WeatherUtil.getBaseTime(currentTime,WeatherUtil.BaseMinuteType.ULTRASHORT_FORECAST_BASEMINUTE),
            55,
            124
        )
        if (nowWeatherDto.response.body == null || ultraShortWeatherDto.response.body == null) {
            return null
        }
        return getNowWeatherDataWithNowAndUltraShort(nowWeatherDto, ultraShortWeatherDto)
    }
}