package com.steelzoo.ownweather.di

import android.content.Context
import androidx.room.Room
import com.steelzoo.ownweather.data.OwnWeatherDatabase
import com.steelzoo.ownweather.data.weather.WeatherDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): OwnWeatherDatabase {
        return Room.databaseBuilder(
            context,
            OwnWeatherDatabase::class.java,
            "ownweather_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesWeatherDao(
        ownWeatherDatabase: OwnWeatherDatabase
    ): WeatherDAO {
        return ownWeatherDatabase.weatherDao()
    }

}