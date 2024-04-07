package com.example.weather2.data

import com.example.weather2.data.room.WeatherEntity
import com.example.weather2.data.room.WeatherDao
import com.example.weather2.response.WeatherResponse

class WeatherRepository(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) {

    suspend fun getWeather(location: String, days: Int, aqi: String, alerts: String): WeatherResponse {
        val key = "8a8b25949fec425599f92306240304"
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
