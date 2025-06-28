package com.example.feature_weather_api.models

import androidx.compose.runtime.Immutable

@Immutable
data class LocationDesc(
    val city: String,
    val state: String?,
    val location: Location
)