package com.example.feature_weather_impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class LocationResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val state: String? = null
)