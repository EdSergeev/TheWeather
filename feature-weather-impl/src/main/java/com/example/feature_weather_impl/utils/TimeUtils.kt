package com.example.feature_weather_impl.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

internal object TimeUtils {
    /**
     * Converts a UTC timestamp to a localized hour string (e.g., "18:00" or "6 PM").
     *
     * @param timestamp The UTC timestamp in seconds.
     * @param locale The desired locale for formatting the time.  Defaults to the system's default locale.
     * @return A string representing the localized hour.
     */
    fun formatTimestampToLocalizedHour(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val zoneId = ZoneId.systemDefault() // Or specify a specific timezone if needed
        val zonedDateTime = instant.atZone(zoneId)

        val formatter = DateTimeFormatter.ofPattern("ha", locale) // "h a" for "6 PM", "HH:00" for "18:00"
            .withLocale(locale)

        return zonedDateTime.format(formatter)
    }

    /**
     * Converts a UTC timestamp to a localized weekday string (e.g., "Monday", "Tuesday").
     *
     * @param timestamp The UTC timestamp in seconds.
     * @param locale The desired locale for formatting the weekday. Defaults to the system's default locale.
     * @return A string representing the localized weekday.
     */
    fun formatTimestampToLocalizedWeekday(timestamp: Long, locale: Locale = Locale.getDefault()): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val zoneId = ZoneId.systemDefault() // Or specify a specific timezone if needed
        val zonedDateTime = instant.atZone(zoneId)

        val formatter = DateTimeFormatter.ofPattern("EEEE", locale) // "EEEE" for full weekday name
            .withLocale(locale)

        return zonedDateTime.format(formatter)
    }
}