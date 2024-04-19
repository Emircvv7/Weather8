package com.example.weather2.presentation.viewmodel

import GetWeatherUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather2.data.model.response.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponse: StateFlow<WeatherResponse?> = _weatherResponse

    fun getWeather(location: String, days: Int, aqi: String, alerts: String) {
        viewModelScope.launch {
            val response = getWeatherUseCase.execute(location, days, aqi, alerts)
            _weatherResponse.value = response
        }
    }
}
