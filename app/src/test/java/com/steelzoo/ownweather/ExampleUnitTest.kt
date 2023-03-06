package com.steelzoo.ownweather

import com.steelzoo.ownweather.data.weather.WeatherUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    @Test
    fun `위경도 XY 좌표 변환 테스트`() {
        assertEquals(
            "60 127",
            WeatherUtil.convertLatLngToGridXY(37.579871128849334, 126.98935225645432)
        )
        assertEquals(
            "97 74",
            WeatherUtil.convertLatLngToGridXY(35.101148844565955, 129.02478725562108)
        )
        assertEquals(
            "53 38",
            WeatherUtil.convertLatLngToGridXY(33.500946412305076, 126.54663058817043)
        )
    }

    @Test
    fun `WeatherUtil baseDate 리턴 값 테스트`() {
        try {
            println(WeatherUtil.getBaseDate(1678123094687 - (1000*60*60*3),WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            assertEquals("20230306",WeatherUtil.getBaseDate(1678123094687 - (1000*60*60*3),WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            println(WeatherUtil.getBaseDate(1678123094687,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            assertEquals("20230307",WeatherUtil.getBaseDate(1678123094687,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
        }catch (e: Exception) {
            println(e.cause)
        }
    }

    @Test
    fun `WeatherUtil baseTime 리턴 값 테스트`() {
        try {
            println(WeatherUtil.getBaseTime(1678123094687+(1000*60*30),WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            assertEquals("0200",WeatherUtil.getBaseTime(1678123094687+(1000*60*30),WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            println(WeatherUtil.getBaseTime(1678123094687,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
            assertEquals("0100",WeatherUtil.getBaseTime(1678123094687,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE))
        }catch (e: Exception) {
            println(e.cause)
        }
    }

    @Test
    fun `getBaseDate, getBaseTime 오차 확인`() {
        var startTime = 0L
        var endTime = 0L

        for (i in 1..10){
            startTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(startTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE)
            endTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(endTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE)

            println(startTime-endTime)
        }

        for (i in 1..10){
            startTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(startTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE)
            endTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(endTime,WeatherUtil.BaseMinuteType.NOWCAST_BASEMINUTE)

            println(startTime-endTime)
        }

    }
}