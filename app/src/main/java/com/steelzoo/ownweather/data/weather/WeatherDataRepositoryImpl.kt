package com.steelzoo.ownweather.data.weather

import android.util.Log
import com.steelzoo.ownweather.data.getNowWeatherDataWithNowAndUltraShort
import com.steelzoo.ownweather.data.toShortForecastDataList
import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
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

    override suspend fun getNowWeatherData(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): NowWeatherDto {
        return remoteSource.getNowWeatherData(baseDate, baseTime, nx, ny)
    }

    override suspend fun getUltraShortForecastData(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): ForecastWeatherDto {
        return remoteSource.getUltraShortForecastData(baseDate, baseTime, nx, ny)
    }

    override suspend fun getShortForecastData(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): ForecastWeatherDto {
        return remoteSource.getShortForecastData(baseDate, baseTime, nx, ny)
    }
}