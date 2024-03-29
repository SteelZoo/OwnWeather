package com.steelzoo.ownweather.data.weather

import androidx.room.*
import com.steelzoo.ownweather.data.weather.model.NowWeatherDtoItem
import com.steelzoo.ownweather.data.weather.model.ShortForecastEntity
import com.steelzoo.ownweather.data.weather.model.UltraShortForecastEntity

@Dao
abstract class WeatherDAO {

    /*
    * UltraShort Forecast Table
    * */
    @Query("SELECT * FROM ultra_short_weather_table")
    abstract suspend fun getUltraShortWeatherData(): List<UltraShortForecastEntity>

    /**
     * Delete all past data and insert only new data
     */
    @Transaction
    open suspend fun saveUltraShortWeatherData(ultraShortWeatherData: List<UltraShortForecastEntity>) {
        deleteAllUltraShortWeatherData()
        insertUltraShortWeather(ultraShortWeatherData)
    }

    /**
     * Don't use insertUltraShortWeather alone,
     *
     * Use saveUltraShortWeatherData
     */
    @Insert
    protected abstract suspend fun insertUltraShortWeather(ultraShortWeatherData: List<UltraShortForecastEntity>)

    @Query("DELETE FROM ultra_short_weather_table")
    abstract suspend fun deleteAllUltraShortWeatherData()


    /*
    * Short Forecast Table
    * */
    @Query("SELECT * FROM short_weather_table")
    abstract suspend fun getShortWeatherData(): List<ShortForecastEntity>

    /**
     * Delete all past data and insert only new data
     */
    @Transaction
    open suspend fun saveShortWeatherData(shortWeatherData: List<ShortForecastEntity>) {
        deleteAllShortWeatherData()
        insertShortWeather(shortWeatherData)
    }

    /**
     * Don't use insertShortWeather alone,
     *
     * Use saveShortWeatherData
     */
    @Insert
    protected abstract suspend fun insertShortWeather(shortWeatherData: List<ShortForecastEntity>)

    @Query("DELETE FROM short_weather_table")
    abstract suspend fun deleteAllShortWeatherData()


    /*
    * Now cast Table
    * */
    @Query("SELECT * FROM now_weather_table")
    abstract suspend fun getNowWeatherData(): List<NowWeatherDtoItem>

    /**
     * Delete all past data and insert only new data
     */
    @Transaction
    open suspend fun saveNowWeatherData(nowWeatherData: List<NowWeatherDtoItem>) {
        deleteAllNowWeatherData()
        insertNowWeather(nowWeatherData)
    }

    /**
     * Don't use insertNowWeather alone,
     *
     * Use saveNowWeatherData
     */
    @Insert
    protected abstract suspend fun insertNowWeather(nowWeatherData: List<NowWeatherDtoItem>)

    @Query("DELETE FROM now_weather_table")
    abstract suspend fun deleteAllNowWeatherData()
}