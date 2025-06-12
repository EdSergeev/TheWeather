package com.example.feature_weather_impl.utils

import android.content.Context
import kotlin.math.roundToInt

internal object WeatherUtils {
    fun formatTemperature(temperature: Float): String {
        return "${temperature.roundToInt()}°"
    }

    /**
     * Gets drawable resource ID from weather icon code
     *
     * @param context Application context
     * @param iconCode Weather icon code (e.g., "01d")
     * @return Resource ID or 0 if not found
     */
    fun getWeatherIconResource(context: Context, iconCode: String): Int {
        val resourceName = "ic_$iconCode"
        return context.resources.getIdentifier(
            resourceName,
            "drawable",
            context.packageName
        )
    }
}