package com.steelzoo.ownweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitClient(){
        return Retrofit.Builder()

    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class