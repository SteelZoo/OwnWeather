package com.steelzoo.ownweather.di

import com.steelzoo.ownweather.data.GO_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @GoRetrofitClient
    @Provides
    @Singleton
    fun providesGoRetrofitClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(GO_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoRetrofitClient