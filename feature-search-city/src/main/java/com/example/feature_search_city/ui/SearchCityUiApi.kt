package com.example.feature_search_city.ui

import com.example.core_data.Data
import com.example.core_ui.model.UiData
import com.example.feature_weather_api.models.LocationDesc

interface SearchCityUiApi {
    data class DomainState(
        val query: String,
        val cities: Data<List<LocationDesc>>,
    )

    data class UiState(
        val query: String,
        val cities: UiData<List<LocationDesc>>,
        val showEmptyResult: Boolean,
    )

    companion object {
        const val QUERY_MIN_LENGTH = 2
    }
}