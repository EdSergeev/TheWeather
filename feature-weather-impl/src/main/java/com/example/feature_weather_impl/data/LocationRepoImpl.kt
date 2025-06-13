package com.example.feature_weather_impl.data

import com.example.feature_weather_api.LocationRepo
import com.example.feature_weather_api.models.Location
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class LocationRepoImpl : LocationRepo {
    private var storedLocation: Location? = null

    private val locationFlow = MutableSharedFlow<Location>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override fun observeLocation(): Flow<Location> {
        return locationFlow
    }

    override fun storeLocation(location: Location) {
        storedLocation = location
        locationFlow.tryEmit(location)
    }

    override fun getLocation(): Location? = storedLocation
}