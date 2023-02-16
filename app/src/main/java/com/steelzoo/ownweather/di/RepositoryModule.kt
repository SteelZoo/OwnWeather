package com.steelzoo.ownweather.di

import com.steelzoo.ownweather.data.weather.WeatherDataRepositoryImpl
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsWeatherDataRepository(weatherDataRepositoryImpl: WeatherDataRepositoryImpl): WeatherDataRepository

}