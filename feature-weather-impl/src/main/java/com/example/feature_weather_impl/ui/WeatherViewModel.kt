package com.example.feature_weather_impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.Data
import com.example.feature_weather_api.LocationRepo
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_impl.service.LocationService
import com.example.feature_weather_impl.ui.WeatherUiApi.DomainState
import com.example.feature_weather_impl.ui.WeatherUiApi.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val locationRepo: LocationRepo,
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
    val hasLocationPermission: StateFlow<Boolean> = _hasLocationPermission

    fun onCreated(viewCreatedScope: CoroutineScope) {
        if (locationRepo.getLocation() == null) {
            requestCurrentLocation()
        }

        locationRepo.observeLocation()
            .distinctUntilChanged()
            .flatMapLatest { location ->
                weatherRepo.getLocationDesc(location)
            }
            .onEach { desc ->
                domainState.update { it.copy(location = desc) }
            }
            .launchIn(viewCreatedScope)

        locationRepo.observeLocation()
            .distinctUntilChanged()
            .flatMapLatest { location ->
                weatherRepo.getWeather(location)
            }
            .onEach { weather ->
                domainState.update { it.copy(weatherSummary = weather) }
            }
            .launchIn(viewCreatedScope)
    }


    fun requestCurrentLocation() {
        viewModelScope.launch {
            val location = locationService.getLastKnownLocation()
            locationRepo.storeLocation(location)
        }
    }

    fun onLocationPermissionUpdated() {
        _hasLocationPermission.tryEmit(locationService.checkLocationPermission())
        requestCurrentLocation()
    }

    fun onRetryClick() {
        val location = locationRepo.getLocation() ?: return
        locationRepo.storeLocation(location)
    }
}