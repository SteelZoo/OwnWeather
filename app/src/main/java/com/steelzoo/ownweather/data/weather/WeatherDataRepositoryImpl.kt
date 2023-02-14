package com.steelzoo.ownweather.data.weather

import com.steelzoo.ownweather.domain.model.WeatherData
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(

) : WeatherDataRepository {
    override fun getNowWeather(): WeatherData {
        TODO("Not yet implemented")
    }
}