package com.example.feature_search_city.ui

internal class SearchCityUiStateMapper {
    fun mapState(state: SearchCityUiApi.DomainState): SearchCityUiApi.UiState {
        return SearchCityUiApi.UiState(
            query = state.query,
            cities = state.cities,
            showEmptyResult = state.query.length >= SearchCityUiApi.QUERY_MIN_LENGTH && state.cities.content.isNullOrEmpty(),
        )
    }
}