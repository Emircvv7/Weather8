package com.example.weather2.data.repository

import com.example.weather2.data.api.WeatherApi
import com.example.weather2.data.dao.WeatherEntity
import com.example.weather2.data.dao.WeatherDao
import com.example.weather2.data.model.response.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) {

    suspend fun getWeather(location: String, days: Int, aqi: String, alerts: String): WeatherResponse {
        val key = "8608d5e7c37542218e344059241904"
        val response = api.getForecast(key, location, days, aqi, alerts)

        val weatherEntity = convertResponseToEntity(response)
        weatherDao.insert(weatherEntity)

        return response
    }


    suspend fun getLocalWeather(): List<WeatherEntity> {
        return weatherDao.getAll()
    }

    private fun convertResponseToEntity(response: WeatherResponse): WeatherEntity {
        val temperature = response.current.temp_c

        return WeatherEntity(
            id = 0,
            temperature = temperature
        )
    }
}
