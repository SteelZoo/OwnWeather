package com.steelzoo.ownweather.ui

import com.steelzoo.ownweather.domain.model.*
import com.steelzoo.ownweather.ui.model.SkyStateUI
import com.steelzoo.ownweather.ui.model.WeatherDataUI

/**
 * domain -> ui
 */


fun SkyState.toSkyStateUI(): SkyStateUI{
    return when(this){
        SkyState(DayState.DAY,CloudState.SUNNY,RainState.NO) -> {SkyStateUI.DAY_SUNNY_NO}
        SkyState(DayState.DAY,CloudState.SUNNY,RainState.RAIN) -> {SkyStateUI.DAY_CLOUDY_RAIN}
        SkyState(DayState.DAY,CloudState.SUNNY,RainState.RAIN_SNOW) -> {SkyStateUI.DAY_SUNNY_RAIN_SNOW}
        SkyState(DayState.DAY,CloudState.SUNNY,RainState.SNOW) -> {SkyStateUI.DAY_SUNNY_SNOW}
        SkyState(DayState.DAY,CloudState.CLOUD_SUNNY,RainState.NO) -> {SkyStateUI.DAY_CLOUD_SUNNY_NO}
        SkyState(DayState.DAY,CloudState.CLOUD_SUNNY,RainState.RAIN) -> {SkyStateUI.DAY_CLOUD_SUNNY_RAIN}
        SkyState(DayState.DAY,CloudState.CLOUD_SUNNY,RainState.RAIN_SNOW) -> {SkyStateUI.DAY_CLOUD_SUNNY_RAIN_SNOW}
        SkyState(DayState.DAY,CloudState.CLOUD_SUNNY,RainState.SNOW) -> {SkyStateUI.DAY_CLOUD_SUNNY_SNOW}
        SkyState(DayState.DAY,CloudState.CLOUDY,RainState.NO) -> {SkyStateUI.DAY_CLOUDY_NO}
        SkyState(DayState.DAY,CloudState.CLOUDY,RainState.RAIN) -> {SkyStateUI.DAY_CLOUDY_RAIN}
        SkyState(DayState.DAY,CloudState.CLOUDY,RainState.RAIN_SNOW) -> {SkyStateUI.DAY_CLOUDY_RAIN_SNOW}
        SkyState(DayState.DAY,CloudState.CLOUDY,RainState.SNOW) -> {SkyStateUI.DAY_CLOUDY_SNOW}
        else -> {SkyStateUI.DAY_SUNNY_NO}
    }
}