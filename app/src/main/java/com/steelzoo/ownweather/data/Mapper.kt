package com.steelzoo.ownweather.data

import com.steelzoo.ownweather.data.weather.model.WeatherData
import com.steelzoo.ownweather.domain.model.NowWeatherData

fun WeatherData.toNowWeatherData(): NowWeatherData {

    var temperature = 0.0
    var oneHourPrecipitation = 0.0
    var eastWestWindComponent = 0.0
    var northSouthWindComponent = 0.0
    var humidity = 0.0
    var precipitationType = 0
    var windDirection = 0.0
    var windSpeed = 0.0

    this.response.body.weatherItems.weatherItemList.forEach { weatherDataItem ->
        when(weatherDataItem.category){
            "T1H" -> {temperature = weatherDataItem.obsrValue}
            "RN1" -> {oneHourPrecipitation = weatherDataItem.obsrValue}
            "UUU" -> {eastWestWindComponent = weatherDataItem.obsrValue}
            "VVV" -> {northSouthWindComponent = weatherDataItem.obsrValue}
            "REH" -> {humidity = weatherDataItem.obsrValue}
            "PTY" -> {precipitationType = weatherDataItem.obsrValue.toInt()}
            "VEC" -> {windDirection = weatherDataItem.obsrValue}
            "WSD" -> {windSpeed = weatherDataItem.obsrValue}
        }
    }

    return NowWeatherData(
        temperature,
        oneHourPrecipitation,
        eastWestWindComponent,
        northSouthWindComponent,
        humidity,
        precipitationType,
        windDirection,
        windSpeed
    )
}