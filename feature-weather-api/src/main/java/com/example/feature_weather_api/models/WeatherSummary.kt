package com.example.feature_weather_api.models

import androidx.compose.runtime.Immutable

@Immutable
data class WeatherSummary(
    val current: Weather,
    val today: List<Weather>,
    val week: List<Weather>
)