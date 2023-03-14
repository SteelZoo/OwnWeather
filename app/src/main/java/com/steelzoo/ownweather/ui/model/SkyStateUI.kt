package com.steelzoo.ownweather.ui.model

import com.steelzoo.ownweather.R


/**
 * 추후 Night 대응
 */
enum class SkyStateUI(val drawableImage: Int) {
    DAY_SUNNY_NO(R.drawable.icon_weather_sun),
    DAY_SUNNY_RAIN(R.drawable.icon_weather_sun_cloud_rain),
    DAY_SUNNY_RAIN_SNOW(R.drawable.icon_weather_sun_cloud_rain),
    DAY_SUNNY_SNOW(R.drawable.icon_weather_sun_cloud_snow),
    DAY_CLOUD_SUNNY_NO(R.drawable.icon_weather_sun_cloud),
    DAY_CLOUD_SUNNY_RAIN(R.drawable.icon_weather_sun_cloud_rain),
    DAY_CLOUD_SUNNY_RAIN_SNOW(R.drawable.icon_weather_sun_cloud_rain),
    DAY_CLOUD_SUNNY_SNOW(R.drawable.icon_weather_sun_cloud_snow),
    DAY_CLOUDY_NO(R.drawable.icon_weather_cloud),
    DAY_CLOUDY_RAIN(R.drawable.icon_weather_cloud_rain),
    DAY_CLOUDY_RAIN_SNOW(R.drawable.icon_weather_cloud_rain),
    DAY_CLOUDY_SNOW(R.drawable.icon_weather_cloud_snow),
}
