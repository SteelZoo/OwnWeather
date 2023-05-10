package com.steelzoo.ownweather

import com.steelzoo.ownweather.data.weather.WeatherUtil
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * 1683567208929 : 230509 0233
 * 3600000 :One Hour
 *
 *
 */
class UnitTest {

    @Test
    fun `위경도 XY 좌표 변환 테스트`() {
        val map1 = WeatherUtil.convertLatLngToGridXY(37.579871128849334, 126.98935225645432)
        val map2 = WeatherUtil.convertLatLngToGridXY(35.101148844565955, 129.02478725562108)
        val map3 = WeatherUtil.convertLatLngToGridXY(33.500946412305076, 126.54663058817043)
        val map4 = WeatherUtil.convertLatLngToGridXY(37.4590264, 126.6763081)

        assertEquals(60, map1["nx"]); assertEquals(127, map1["ny"])
        assertEquals(97, map2["nx"]); assertEquals(74, map2["ny"])
        assertEquals(53, map3["nx"]); assertEquals(38, map3["ny"])

        println(map1)
        println(map2)
        println(map3)
        println(map4)
    }

    @Test
    fun `WeatherUtil baseDate 리턴 값 테스트`() {
        try {
            println(WeatherUtil.getBaseDate(1678123094687 - (1000*60*60*3),WeatherUtil.BaseTimeType.NOWCAST))
            assertEquals("20230306",WeatherUtil.getBaseDate(1678123094687 - (1000*60*60*3),WeatherUtil.BaseTimeType.NOWCAST))
            println(WeatherUtil.getBaseDate(1678123094687,WeatherUtil.BaseTimeType.NOWCAST))
            assertEquals("20230307",WeatherUtil.getBaseDate(1678123094687,WeatherUtil.BaseTimeType.NOWCAST))

            //baseDate 버그 현상 확인
            println()
            println(WeatherUtil.getBaseDate(1683556408929,WeatherUtil.BaseTimeType.NOWCAST))
            println(WeatherUtil.getBaseTime(1683556408929,WeatherUtil.BaseTimeType.NOWCAST))
            println(SimpleDateFormat("yyyyMMddHHmm").format(1683556408929))

            println()
            println(WeatherUtil.getBaseDate(1683556408929+(1000*60*20),WeatherUtil.BaseTimeType.NOWCAST))
            println(WeatherUtil.getBaseTime(1683556408929+(1000*60*20),WeatherUtil.BaseTimeType.NOWCAST))
            println(SimpleDateFormat("yyyyMMddHHmm").format(1683556408929+(1000*60*20)))
        }catch (e: Exception) {
            println(e.cause)
        }
    }

    @Test
    fun `WeatherUtil baseTime 리턴 값 테스트`() {
        try {
            println(WeatherUtil.getBaseTime(1678123094687+(1000*60*30),WeatherUtil.BaseTimeType.NOWCAST))
            assertEquals("0200",WeatherUtil.getBaseTime(1678123094687+(1000*60*30),WeatherUtil.BaseTimeType.NOWCAST))
            println(WeatherUtil.getBaseTime(1678123094687,WeatherUtil.BaseTimeType.NOWCAST))
            assertEquals("0100",WeatherUtil.getBaseTime(1678123094687,WeatherUtil.BaseTimeType.NOWCAST))
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
            WeatherUtil.getBaseDate(startTime,WeatherUtil.BaseTimeType.NOWCAST)
            endTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(endTime,WeatherUtil.BaseTimeType.NOWCAST)

            println(startTime-endTime)
        }

        for (i in 1..10){
            startTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(startTime,WeatherUtil.BaseTimeType.NOWCAST)
            endTime = System.currentTimeMillis()
            WeatherUtil.getBaseDate(endTime,WeatherUtil.BaseTimeType.NOWCAST)

            println(startTime-endTime)
        }

    }

    @Test
    fun `getBaseTime 단기예보(SHORT_FORECAST) 동작확인`() {
        val hourMinuteDateFormat = SimpleDateFormat("yyyyMMddHHmm")
        val hourMinuteDateFormat2 = SimpleDateFormat("HHmm")

        assertEquals("2300",WeatherUtil.getBaseTime(hourMinuteDateFormat2.parse("0209").time,WeatherUtil.BaseTimeType.SHORT_FORECAST))
        assertEquals("0200",WeatherUtil.getBaseTime(hourMinuteDateFormat2.parse("0210").time,WeatherUtil.BaseTimeType.SHORT_FORECAST))
        assertEquals("0200",WeatherUtil.getBaseTime(hourMinuteDateFormat2.parse("0356").time,WeatherUtil.BaseTimeType.SHORT_FORECAST))

    }


}