package com.example.feature_search_city.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.Data
import com.example.feature_search_city.ui.SearchCityUiApi.DomainState
import com.example.feature_search_city.ui.SearchCityUiApi.UiState
import com.example.feature_weather_api.LocationRepo
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_api.models.LocationDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class SearchCityViewModel(
    private val locationRepo: LocationRepo,
    private val uiStateMapper: SearchCityUiStateMapper,
    private val weatherRepo: WeatherRepo,
) : ViewModel() {

    private val domainState by lazy {
        MutableStateFlow(
            DomainState(
                query = "",
                cities = Data.success(emptyList()),
            )
        )
    }

    private val queryFlow = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val uiState: StateFlow<UiState> = domainState
        .mapLatest { uiStateMapper.mapState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = uiStateMapper.mapState(domainState.value),
        )

    fun onCreated(viewCreatedScope: CoroutineScope) {
        queryFlow
            .debounce(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS)
            .flatMapLatest { query ->
                if (query.length >= SearchCityUiApi.QUERY_MIN_LENGTH) {
                    weatherRepo.findLocationByQuery(query)
                } else {
                    flowOf(Data.success(emptyList()))
                }
            }
            .onEach { cities ->
                domainState.update { it.copy(cities = cities) }
            }
            .launchIn(viewCreatedScope)
    }

    fun onQueryChange(query: String) {
        if (domainState.value.query == query) {
            return
        }

        domainState.update { it.copy(query = query) }
        queryFlow.tryEmit(query)
    }

    fun onCityClicked(city: LocationDesc) {
        locationRepo.storeLocation(city.location)
    }
}