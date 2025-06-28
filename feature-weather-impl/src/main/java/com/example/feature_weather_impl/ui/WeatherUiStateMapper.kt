package com.example.feature_weather_impl.ui

import com.example.core_data.Data
import com.example.core_ui.model.UiData
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_api.models.WeatherSummary
import com.example.feature_weather_impl.ui.WeatherUiApi.DomainState
import com.example.feature_weather_impl.ui.WeatherUiApi.UiState

internal class WeatherUiStateMapper {
    fun mapState(domainState: DomainState): UiState {
        return UiState(
            city = mapLocation(domainState.location),
            weather = mapWeather(domainState.weatherSummary),
        )
    }

    private fun mapLocation(locationData: Data<LocationDesc>): UiData<String> {
        return UiData(
            isLoading = locationData.isLoading,
            content = locationData.content?.city,
            error = locationData.error
        )
    }

    private fun mapWeather(weatherData: Data<WeatherSummary>): UiData<WeatherSummary> {
        return UiData(
            isLoading = weatherData.isLoading,
            content = weatherData.content,
            error = weatherData.error
        )
    }
}