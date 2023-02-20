package com.steelzoo.ownweather.data.weather

object WeatherUtil {
    /**
     * 경위도 좌표계를 기상청 XY 좌표계로 변경해주는 메소드
     */
    fun convertLatLngToGridXY(lat: Double, lng: Double): String{
        val resultString = StringBuilder()

        val RE = 6371.00877 // 지구 반경(km)
        val GRID = 5.0 // 격자 간격(km)
        val SLAT1 = 30.0 // 투영 위도1(degree)
        val SLAT2 = 60.0 // 투영 위도2(degree)
        val OLON = 126.0 // 기준점 경도(degree)
        val OLAT = 38.0 // 기준점 위도(degree)
        val XO = 43.0 // 기준점 X좌표(GRID)
        val YO = 136.0 // 기1준점 Y좌표(GRID)

        val DEGRAD = Math.PI / 180.0
        val RADDEG = 180.0 / Math.PI

        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)


        var ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5)
        ra = re * sf / Math.pow(ra, sn)
        var theta: Double = lng * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn
        resultString.append(Math.floor(ra * Math.sin(theta) + XO + 0.5).toInt())
        resultString.append(" ")
        resultString.append(Math.floor(ro - ra * Math.cos(theta) + YO + 0.5).toInt())
//        rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5)
//        rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5)

        return resultString.toString()
    }
}