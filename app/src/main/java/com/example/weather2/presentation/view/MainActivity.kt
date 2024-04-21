package com.example.weather2.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.weather2.data.dao.AppDatabase
import com.example.weather2.data.dao.WeatherDao
import com.example.weather2.databinding.ActivityMainBinding
import com.example.weather2.presentation.view.adapter.WeatherAdapter
import com.example.weather2.presentation.viewmodels.WeatherViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = WeatherAdapter()
    private lateinit var weatherDao: WeatherDao

    private val locations = listOf("London", "New York", "Paris", "Moscow", "Bishkek")
    private var currentLocationIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "weather-database"
        ).build()

        weatherDao = db.weatherDao()

        binding.recyclerView.adapter = adapter

        val days = 7
        val aqi = "no"
        val alerts = "no"

        getWeatherForLocation(locations[currentLocationIndex], days, aqi, alerts)

        binding.arrow.setOnClickListener {
            currentLocationIndex = (currentLocationIndex + 1) % locations.size
            getWeatherForLocation(locations[currentLocationIndex], days, aqi, alerts)
        }
    }

    private fun getWeatherForLocation(location: String, days: Int, aqi: String, alerts: String) {
        viewModel.getWeather(location, days, aqi, alerts)

        lifecycleScope.launch {
            viewModel.weatherResponse.collect { response ->
                response?.let {
                    val hoursWithDays = it.forecast.forecastday.flatMap { day ->
                        day.hour.map { hour -> Pair(hour, day) }
                    }
                    val sortedHoursWithDays = hoursWithDays.sortedBy { it.first.time_epoch }
                    adapter.submitList(sortedHoursWithDays)

                    val currentWeather = it.current
                    binding.tvToday.text = "Today"
                    binding.tvGradus.text = "${currentWeather.temp_c}°C"
                    binding.tvWeather.text = currentWeather.condition.text
                    binding.tvCity.text = it.location.name

                    val iconUrl = "https:${currentWeather.condition.icon}"

                    Picasso.get()
                        .load(iconUrl)
                        .into(binding.ivIcon)
                }
            }
        }
    }
}
