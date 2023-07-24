package com.steelzoo.ownweather.data

import com.steelzoo.ownweather.data.weather.model.ForecastWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDto
import com.steelzoo.ownweather.data.weather.model.NowWeatherDtoItem
import com.steelzoo.ownweather.domain.model.*
import com.steelzoo.ownweather.domain.model.data.NowCastData
import com.steelzoo.ownweather.domain.model.data.ShortForecastData


/**
 * data -> domain
 */


/**
 * 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4)
 *
 * 강수형태(PTY) 코드 : (초단기) 없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)
 *
 * (단기) 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
 *
 * 실황 RN1 : Double|Int / 단기 RN1,초단기 PCP : String(강수없음|Double+"mm")
 */
fun getNowWeatherDataWithNowAndUltraShort(
    nowWeatherDto: NowWeatherDto,
    ultraShortWeatherDto: ForecastWeatherDto
): NowCastData {
    val baseTimeStringBuilder = StringBuilder()
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
        if (baseTimeStringBuilder.isEmpty()){
            baseTimeStringBuilder.append(weatherDataItem.baseTime)
        }
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

    return NowCastData(
        baseTimeStringBuilder.toString(),
        temperature,
        oneHourPrecipitation,
        eastWestWindComponent,
        northSouthWindComponent,
        humidity,
        windDirection,
        windSpeed
    )
}


fun List<NowWeatherDtoItem>.toNowCastData(): NowCastData {

    var temperature = 0.0
    var oneHourPrecipitation = 0.0
    var eastWestWindComponent = 0.0
    var northSouthWindComponent = 0.0
    var humidity = 0.0
    var windDirection = 0.0
    var windSpeed = 0.0

    this.forEach { weatherDataItem ->
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

    return NowCastData(
        this.first().baseTime,
        temperature,
        oneHourPrecipitation,
        eastWestWindComponent,
        northSouthWindComponent,
        humidity,
        windDirection,
        windSpeed
    )
}

fun ForecastWeatherDto.toShortForecastDataList() : List<ShortForecastData>{

    val resultList = mutableListOf<ShortForecastData>()

    response.body.weatherItems.weatherItemList
        .groupBy {item ->
            item.fcstDate
        }
        .values
        .sortedBy {
            it[0].fcstDate.toInt()
        }
        .forEach{ datelist ->

            datelist.groupBy { it.fcstTime }
                .values
                .forEach{ dateTimeList ->
                    var date = dateTimeList[0].fcstDate
                    var time = dateTimeList[0].fcstTime
                    var dayState = DayState.DAY
                    var cloudState = CloudState.SUNNY
                    var rainState = RainState.NO
                    var temperature = 0
                    var humidity = 0
                    var precipitation = ""
                    var probability = -1

                    dateTimeList.forEach{ item ->
                        when(item.category){
                            "T1H","TMP" -> {temperature = item.fcstValue.toInt()}
                            "REH" -> {humidity = item.fcstValue.toInt()}
                            "RN1","PCP" -> {precipitation = item.fcstValue}
                            "POP" -> {probability = item.fcstValue.toInt()}
                            "SKY" -> {
                                when(item.fcstValue.toInt()) {
                                1,2 -> {cloudState = CloudState.SUNNY}
                                3 -> {cloudState = CloudState.CLOUD_SUNNY}
                                4 -> {cloudState = CloudState.CLOUDY}
                                }
                            }
                            "PTY" -> {
                                when(item.fcstValue.toInt()){
                                    0 -> {rainState = RainState.NO}
                                    1,5 -> {rainState = RainState.RAIN}
                                    2,6 -> {rainState = RainState.RAIN_SNOW}
                                    3,7 -> {rainState = RainState.SNOW}
                                }
                            }
                        }
                    }

                    resultList.add(
                        ShortForecastData(
                            date,
                            time,
                            SkyState(dayState, cloudState, rainState),
                            temperature, humidity, precipitation, probability
                        )
                    )
                }
        }

    return resultList

//        .sortedWith { o1, o2 ->
//            when {
//                o1.fcstDate.toInt() > o2.fcstDate.toInt() -> 1
//                o1.fcstDate.toInt() == o2.fcstDate.toInt() -> {
//                    if (o1.fcstTime.toInt() < o2.fcstTime.toInt()) -1
//                    else if (o1.fcstTime.toInt() > o2.fcstTime.toInt()) 1
//                    else 0
//                }
//                o1.fcstDate.toInt() < o2.fcstDate.toInt() -> -1
//                else -> 0
//            }
//        }



}