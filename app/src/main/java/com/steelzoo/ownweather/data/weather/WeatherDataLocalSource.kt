package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.data.weather.model.NowWeatherDtoItem
import com.steelzoo.ownweather.data.weather.model.ShortForecastEntity
import com.steelzoo.ownweather.data.weather.model.UltraShortForecastEntity
import javax.inject.Inject

class WeatherDataLocalSource @Inject constructor(
    val weatherDAO: WeatherDAO
) {

    suspend fun getNowWeatherData(): List<NowWeatherDtoItem> {
        return weatherDAO.getNowWeatherData()
    }

    suspend fun getUltraShortForecastData(): List<UltraShortForecastEntity> {
        return weatherDAO.getUltraShortWeatherData()
    }

    suspend fun getShortForecastData(): List<ShortForecastEntity> {
        return weatherDAO.getShortWeatherData()
    }

    suspend fun saveNowWeatherData(nowWeatherDtoItemList: List<NowWeatherDtoItem>){
        weatherDAO.saveNowWeatherData(nowWeatherDtoItemList)
    }

    suspend fun saveUltraShortForecastData(ultraShortForecastEntityList: List<UltraShortForecastEntity>){
        weatherDAO.saveUltraShortWeatherData(ultraShortForecastEntityList)
    }

    suspend fun saveShortForecastData(ShortForecastEntityList: List<ShortForecastEntity>){
        weatherDAO.saveShortWeatherData(ShortForecastEntityList)
    }

}