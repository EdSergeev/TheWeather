package com.example.feature_weather_api.models

import java.sql.Timestamp

class Weather(
    val temperature: Float,
    val iconId: String,
    val timestamp: Long,
)