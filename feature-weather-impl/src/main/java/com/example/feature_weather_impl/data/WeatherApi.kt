import com.example.feature_weather_impl.data.models.LocationResponse
import com.example.feature_weather_impl.data.models.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class WeatherApi(
    private val apiKey: String,
) {
    private val httpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis = 30_000
        }
    }

    /**
     * Get weather data for specific coordinates
     *
     * @param lat Latitude
     * @param lon Longitude
     * @param exclude Parts to exclude (minutely, hourly, daily, alerts)
     * @param units Units of measurement (standard, metric, imperial)
     * @param lang Language for weather descriptions
     */
    suspend fun getWeather(
        lat: Double,
        lon: Double,
        exclude: List<String> = emptyList(),
        units: String = "metric",
        lang: String = "en"
    ): Result<WeatherResponse> {
        return try {
            val response = httpClient.get("https://api.openweathermap.org/data/3.0/onecall") {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("units", units)
                parameter("lang", lang)
                parameter("appid", apiKey)

                if (exclude.isNotEmpty()) {
                    parameter("exclude", exclude.joinToString(","))
                }
            }

            Result.success(response.body<WeatherResponse>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocationDesc(
        lat: Double,
        lon: Double,
        limit: Int = 1
    ): Result<List<LocationResponse>> {
        return try {
            val response = httpClient.get("https://api.openweathermap.org/geo/1.0/reverse") {
                parameter("lat", lat)
                parameter("lon", lon)
                parameter("limit", limit)
                parameter("appid", apiKey)
            }
            Result.success(response.body<List<LocationResponse>>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun findLocation(query: String, limit: Int = 5): Result<List<LocationResponse>> {
        return try {
            val response = httpClient.get("https://api.openweathermap.org/geo/1.0/direct") {
                parameter("q", query)
                parameter("limit", limit)
                parameter("appid", apiKey)
            }
            Result.success(response.body<List<LocationResponse>>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}