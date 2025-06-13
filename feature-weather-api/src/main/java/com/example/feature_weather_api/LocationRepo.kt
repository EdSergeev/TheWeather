package com.example.feature_weather_api

import com.example.feature_weather_api.models.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepo {
    fun observeLocation(): Flow<Location>
    fun storeLocation(location: Location)
    fun getLocation(): Location?
}