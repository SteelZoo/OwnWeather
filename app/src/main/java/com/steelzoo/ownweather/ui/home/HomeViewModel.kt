package com.steelzoo.ownweather.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steelzoo.ownweather.domain.repositoryinterface.WeatherDataRepository
import com.steelzoo.ownweather.domain.usecase.GetNowWeatherUseCase
import com.steelzoo.ownweather.domain.usecase.GetShortForecastUseCase
import com.steelzoo.ownweather.ui.model.ShortForecastItem
import com.steelzoo.ownweather.ui.model.WeatherDataUI
import com.steelzoo.ownweather.ui.toShortForecastItemList
import com.steelzoo.ownweather.ui.toWeatherDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNowWeatherUseCase: GetNowWeatherUseCase,
    private val getShortForecastUseCase: GetShortForecastUseCase
): ViewModel() {

    private val _nowWeather = MutableLiveData<WeatherDataUI>()
    val nowWeather: LiveData<WeatherDataUI>
        get() = _nowWeather

    private val _shortForecast = MutableLiveData<List<ShortForecastItem>>()
    val shortForecast: LiveData<List<ShortForecastItem>>
        get() = _shortForecast

    fun getNowWeather(lat: Double, lng: Double){
        viewModelScope.launch {
            getNowWeatherUseCase(lat, lng)?.let {nowWeatherData ->
                _nowWeather.postValue(nowWeatherData.toWeatherDataUI().also {
                    Log.d("FORECAST", "getNowWeather: $it")
                })
            }
        }
    }

    fun getShortForecast(lat: Double, lng: Double){
        viewModelScope.launch {
            Log.d("FORECAST", "getShortForecast: start")
            getShortForecastUseCase(lat, lng)?.let {shortForecastDataList ->
                _shortForecast.postValue(shortForecastDataList.toShortForecastItemList().also {
                    Log.d(
                        "FORECAST",
                        "getShortForecast: $it"
                    ) })
            }
        }
    }

}