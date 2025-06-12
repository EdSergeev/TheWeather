package com.example.feature_weather_impl.data.mappers

import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_impl.data.models.LocationResponse

internal fun LocationResponse.toDomain(): LocationDesc = LocationDesc(
    city = name,
    state = state,
    location = Location(latitude = lat, longitude = lon)
)
