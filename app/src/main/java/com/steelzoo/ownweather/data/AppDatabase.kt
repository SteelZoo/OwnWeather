package com.steelzoo.ownweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.steelzoo.ownweather.data.weather.WeatherDAO
import com.steelzoo.ownweather.data.weather.model.NowWeatherDtoItem
import com.steelzoo.ownweather.data.weather.model.ShortForecastEntity
import com.steelzoo.ownweather.data.weather.model.UltraShortForecastEntity

@Database(
    entities = [NowWeatherDtoItem::class, UltraShortForecastEntity::class, ShortForecastEntity::class],
    version = 1
)
abstract class OwnWeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}