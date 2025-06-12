package com.example.feature_weather_api.models

class WeatherSummary(
    val current: Weather,
    val today: List<Weather>,
    val week: List<Weather>
)