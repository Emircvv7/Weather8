import com.example.weather2.data.repository.WeatherRepository
import com.example.weather2.data.model.response.WeatherResponse
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend fun execute(location: String, days: Int, aqi: String, alerts: String): WeatherResponse {
        return repository.getWeather(location, days, aqi, alerts)
    }
}
