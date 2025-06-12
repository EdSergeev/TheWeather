package com.example.feature_weather_impl.data.mappers

import com.example.feature_weather_api.models.WeatherSummary
import com.example.feature_weather_impl.data.models.WeatherResponse
import com.example.feature_weather_api.models.Weather as DomainWeather

internal fun WeatherResponse.toDomain(): WeatherSummary {
    val currentWeather = current?.let {
        val weatherItem = it.weather.first()
        DomainWeather(
            temperature = it.temp,
            iconId = weatherItem.icon,
            timestamp = it.dt
        )
    } ?: throw IllegalStateException("Current weather is null")

    val today = hourly?.map {
        val weatherItem = it.weather.first()
        DomainWeather(
            temperature = it.temp,
            iconId = weatherItem.icon,
            timestamp = it.dt
        )
    } ?: throw IllegalStateException("Hourly weather is null")

    val week = daily?.take(7)?.map {
        val weatherItem = it.weather.first()
        DomainWeather(
            temperature = it.temp.eve,
            iconId = weatherItem.icon,
            timestamp = it.dt
        )
    } ?: throw IllegalStateException("Daily weather is null")

    return WeatherSummary(
        current = currentWeather,
        today = today,
        week = week
    )
}