package com.example.feature_weather_api.models

import androidx.compose.runtime.Immutable

@Immutable
data class Weather(
    val temperature: Float,
    val iconId: String,
    val timestamp: Long,
)