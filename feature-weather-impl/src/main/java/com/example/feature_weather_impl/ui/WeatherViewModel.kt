package com.example.feature_weather_impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.Data
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_impl.service.LocationService
import com.example.feature_weather_impl.ui.WeatherUiApi.DomainState
import com.example.feature_weather_impl.ui.WeatherUiApi.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeatherViewModel(
    private val uiStateMapper: WeatherUiStateMapper,
    private val weatherRepo: WeatherRepo,
    private val locationService: LocationService,
) : ViewModel() {

    private val domainState by lazy {
        MutableStateFlow(
            DomainState(
                location = Data.loading(),
                weatherSummary = Data.loading(),
            )
        )
    }

    val uiState: StateFlow<UiState> = domainState
        .mapLatest { uiStateMapper.mapState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = uiStateMapper.mapState(domainState.value),
        )

    private val _hasLocationPermission = MutableStateFlow(locationService.checkLocationPermission())

    private val locationFlow = MutableSharedFlow<Location>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )


    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    fun onCreated() {
        requestLocation()

        locationFlow
            .flatMapLatest { location ->
                weatherRepo.getLocationDesc(location)
            }
            .onEach { desc ->
                domainState.update { it.copy(location = desc) }
            }
            .launchIn(viewModelScope)

        locationFlow
            .flatMapLatest { location ->
                weatherRepo.getWeather(location)
            }
            .onEach { weather ->
                domainState.update { it.copy(weatherSummary = weather) }
            }
            .launchIn(viewModelScope)
    }

    fun requestLocation() {
        viewModelScope.launch {
            val location = locationService.getLastKnownLocation()
            locationFlow.emit(location)
        }
    }

    fun onRetryClick() {
        locationFlow.replayCache.firstOrNull()?.let { location ->
            locationFlow.tryEmit(location)
        }
    }
}