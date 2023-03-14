package com.steelzoo.ownweather.data

import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import com.steelzoo.ownweather.domain.model.*


/**
 * data -> domain
 */


/**
 * 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
 *
 * 강수형태(PTY) 코드 : (초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)
 *
 * (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
 */
fun getNowWeatherDataWithNowAndUltraShort(
    nowWeatherDto: NowWeatherDto,
    ultraShortWeatherDto: ForecastWeatherDto
): NowWeatherData{
    var dayState = DayState.DAY
    var cloudState = CloudState.SUNNY
    var rainState = RainState.NO
    var temperature = 0.0
    var oneHourPrecipitation = 0.0
    var eastWestWindComponent = 0.0
    var northSouthWindComponent = 0.0
    var humidity = 0.0
    var windDirection = 0.0
    var windSpeed = 0.0

    nowWeatherDto.response.body.weatherItems.weatherItemList.forEach { weatherDataItem ->
        when(weatherDataItem.category){
            "T1H" -> {temperature = weatherDataItem.obsrValue}
            "RN1" -> {oneHourPrecipitation = weatherDataItem.obsrValue}
            "UUU" -> {eastWestWindComponent = weatherDataItem.obsrValue}
            "VVV" -> {northSouthWindComponent = weatherDataItem.obsrValue}
            "REH" -> {humidity = weatherDataItem.obsrValue}
            "VEC" -> {windDirection = weatherDataItem.obsrValue}
            "WSD" -> {windSpeed = weatherDataItem.obsrValue}
            "PTY" -> {
                when(weatherDataItem.obsrValue.toInt()){
                    0 -> {rainState = RainState.NO}
                    1,5 -> {rainState = RainState.RAIN}
                    2,6 -> {rainState = RainState.RAIN_SNOW}
                    3,7 -> {rainState = RainState.SNOW}
                }
            }
        }
    }

    for (forecastWeatherItem in ultraShortWeatherDto.response.body.weatherItems.weatherItemList){
        if (forecastWeatherItem.category == "SKY"){
            when(forecastWeatherItem.fcstValue.toInt()){
                1,2 -> {cloudState = CloudState.SUNNY}
                3 -> {cloudState = CloudState.CLOUD_SUNNY}
                4 -> {cloudState = CloudState.CLOUDY}
            }
            break
        }
    }

    return NowWeatherData(
        SkyState(dayState, cloudState, rainState),
        temperature,
        oneHourPrecipitation,
        eastWestWindComponent,
        northSouthWindComponent,
        humidity,
        windDirection,
        windSpeed
    )
}

/**
 * @deprecated
 */
fun NowWeatherDto.toNowWeatherData(): NowWeatherData {

    var temperature = 0.0
    var oneHourPrecipitation = 0.0
    var eastWestWindComponent = 0.0
    var northSouthWindComponent = 0.0
    var humidity = 0.0
    var windDirection = 0.0
    var windSpeed = 0.0

    this.response.body.weatherItems.weatherItemList.forEach { weatherDataItem ->
        when(weatherDataItem.category){
            "T1H" -> {temperature = weatherDataItem.obsrValue}
            "RN1" -> {oneHourPrecipitation = weatherDataItem.obsrValue}
            "UUU" -> {eastWestWindComponent = weatherDataItem.obsrValue}
            "VVV" -> {northSouthWindComponent = weatherDataItem.obsrValue}
            "REH" -> {humidity = weatherDataItem.obsrValue}
            "PTY" -> {}
            "VEC" -> {windDirection = weatherDataItem.obsrValue}
            "WSD" -> {windSpeed = weatherDataItem.obsrValue}
        }
    }

    return NowWeatherData(
        SkyState(DayState.DAY,CloudState.SUNNY,RainState.NO),
        temperature,
        oneHourPrecipitation,
        eastWestWindComponent,
        northSouthWindComponent,
        humidity,
        windDirection,
        windSpeed
    )
}

