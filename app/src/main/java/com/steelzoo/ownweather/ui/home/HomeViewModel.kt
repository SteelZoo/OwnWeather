package com.steelzoo.ownweather.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val weatherDataRepository: WeatherDataRepository
): ViewModel() {

    fun getNowWeather(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("WEATHER_DATA_TEST", weatherDataRepository.getNowWeather())
        }
    }

}