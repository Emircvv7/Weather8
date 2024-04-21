package com.example.weather2.presentation.viewmodels

import GetWeatherUseCase
import com.example.weather2.data.model.response.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel() {

    private val _weatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponse: StateFlow<WeatherResponse?> = _weatherResponse

    fun getWeather(location: String, days: Int, aqi: String, alerts: String) {
        _uiState.value = UiState.Loading
        launch {
            val response = getWeatherUseCase.execute(location, days, aqi, alerts)
            if (response is Resource.Success) {
                _uiState.value = UiState.Success(response.data)
                _weatherResponse.value = response.data
            } else if (response is Resource.Error) {
                _uiState.value = UiState.Error(response.message)
            }
        }
    }
}
