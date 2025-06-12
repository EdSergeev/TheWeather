package com.example.feature_weather_impl.ui

import com.example.core_data.Data
import com.example.core_data.mapContent
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_impl.ui.WeatherUiApi.DomainState
import com.example.feature_weather_impl.ui.WeatherUiApi.UiState

internal class WeatherUiStateMapper {
    fun mapState(domainState: DomainState): UiState {
        return UiState(
            city = mapLocation(domainState.location),
            weather = Data.loading(),
            showRetryButton = false
        )
    }

    private fun mapLocation(locationData: Data<LocationDesc>): Data<String> {
        return locationData.mapContent { it.city }
    }
}