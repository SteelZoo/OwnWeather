package com.steelzoo.ownweather.domain.model

data class SkyState(
    val dayState: DayState,
    val cloudState: CloudState,
    val rainState: RainState
)

enum class DayState {
    DAY,
    NIGHT
}

enum class CloudState {
    SUNNY,
    CLOUD_SUNNY,
    CLOUDY
}

enum class RainState {
    NO,
    RAIN,
    RAIN_SNOW,
    SNOW
}