package com.example.feature_weather_impl.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int,
    val current: CurrentWeather? = null,
    val minutely: List<MinutelyWeather>? = null,
    val hourly: List<HourlyWeather>? = null,
    val daily: List<DailyWeather>? = null,
    val alerts: List<WeatherAlert>? = null
)

@Serializable
internal data class CurrentWeather(
    val dt: Long,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int? = null,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>
)

@Serializable
internal data class MinutelyWeather(
    val dt: Long,
    val precipitation: Double
)

@Serializable
internal data class HourlyWeather(
    val dt: Long,
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int? = null,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>,
    val pop: Double
)

@Serializable
internal data class DailyWeather(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long? = null,
    val moonset: Long? = null,
    @SerialName("moon_phase")
    val moonPhase: Double? = null,
    val summary: String? = null,
    val temp: DailyTemp,
    @SerialName("feels_like")
    val feelsLike: DailyFeelsLike,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("wind_deg")
    val windDeg: Int,
    @SerialName("wind_gust")
    val windGust: Double? = null,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double,
    val rain: Double? = null,
    val snow: Double? = null,
    val uvi: Double
)

@Serializable
internal data class DailyTemp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
internal data class DailyFeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
internal data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
internal data class WeatherAlert(
    @SerialName("sender_name")
    val senderName: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String,
    val tags: List<String> = emptyList()
)