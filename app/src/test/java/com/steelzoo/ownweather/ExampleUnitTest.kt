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
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `위경도 XY 좌표 변환 테스트`() {
        assertEquals(
            "60 127",
            WeatherUtil.convertLatLngToGridXY(37.579871128849334,126.98935225645432)
        )
        assertEquals(
            "97 74",
            WeatherUtil.convertLatLngToGridXY(35.101148844565955,129.02478725562108)
        )
        assertEquals(
            "53 38",
            WeatherUtil.convertLatLngToGridXY(33.500946412305076,126.54663058817043)
        )
    }
}