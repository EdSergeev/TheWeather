package com.example.feature_weather_api

import com.example.core_data.Data
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_api.models.WeatherSummary
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    fun getWeather(location: Location): Flow<Data<WeatherSummary>>
    fun getLocationDesc(location: Location): Flow<Data<LocationDesc>>
    fun findLocationByQuery(query: String): Flow<Data<List<LocationDesc>>>
}