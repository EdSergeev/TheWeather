package com.example.feature_search_city.ui

import com.example.core_data.Data
import com.example.core_ui.model.UiData
import com.example.feature_weather_api.models.LocationDesc

internal class SearchCityUiStateMapper {
    fun mapState(state: SearchCityUiApi.DomainState): SearchCityUiApi.UiState {
        return SearchCityUiApi.UiState(
            query = state.query,
            cities = mapCities(state.cities),
            showEmptyResult = state.query.length >= SearchCityUiApi.QUERY_MIN_LENGTH && state.cities.content.isNullOrEmpty(),
        )
    }

    private fun mapCities(cities : Data<List<LocationDesc>>): UiData<List<LocationDesc>> {
        return UiData(
            content = cities.content,
            isLoading = cities.isLoading,
            error = cities.error,
        )
    }
}