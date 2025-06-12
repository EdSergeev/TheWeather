package com.example.feature_weather_impl.ui

import com.example.core_data.Data
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_api.models.WeatherSummary

internal interface WeatherUiApi {
    data class DomainState(
        val location: Data<LocationDesc>,
        val weatherSummary: Data<WeatherSummary>,
    )

    data class UiState(
        val city: Data<String>,
        val weather: Data<Weather>,
        val showRetryButton: Boolean
    ) {
        data class Weather(
            val currentTemp: Float,
            val currentWeatherIconUrl: String,
        )
    }
}