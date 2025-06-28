package com.example.feature_weather_api.models

import androidx.compose.runtime.Immutable

@Immutable
data class Location(
    val latitude: Double,
    val longitude: Double
)