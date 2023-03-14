package com.steelzoo.ownweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import com.steelzoo.ownweather.ui.model.WeatherDataUI
import com.steelzoo.ownweather.ui.toWeatherDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val weatherDataRepository: WeatherDataRepository
): ViewModel() {

    private val _nowWeather = MutableLiveData<WeatherDataUI>()
    val nowWeather: LiveData<WeatherDataUI>
        get() = _nowWeather

    init {
        getNowWeather()
    }

    private fun getNowWeather(){
        viewModelScope.launch(Dispatchers.IO) {
            weatherDataRepository.getNowWeatherData()?.let {nowWeatherData ->
                _nowWeather.postValue(nowWeatherData.toWeatherDataUI())
            }
        }
    }

}