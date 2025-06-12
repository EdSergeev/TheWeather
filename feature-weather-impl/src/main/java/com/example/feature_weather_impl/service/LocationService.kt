package com.example.feature_weather_impl.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.feature_weather_api.models.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

internal class LocationService(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    private val defaultLocation by lazy { Location(latitude = 51.5128, longitude = -0.0918) }

    fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location {
        return try {
            val location = fusedLocationClient.lastLocation.await()
            location?.let {
                Location(location.latitude, location.longitude)
            } ?: defaultLocation
        } catch (e: SecurityException) {
            // Handle permission issues
            defaultLocation
        } catch (e: Exception) {
            // Handle other exceptions
            defaultLocation
        }
    }
}