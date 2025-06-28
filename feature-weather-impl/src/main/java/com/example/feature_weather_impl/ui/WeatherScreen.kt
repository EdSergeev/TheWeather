package com.example.feature_weather_impl.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature_weather_impl.R
import com.example.feature_weather_impl.ui.widgets.CurrentWeatherView
import com.example.feature_weather_impl.ui.widgets.LoadingCurrentWeatherView
import com.example.feature_weather_impl.ui.widgets.TodayWeatherView
import com.example.feature_weather_impl.ui.widgets.WeatherHeaderView
import com.example.feature_weather_impl.ui.widgets.WeekWeatherView
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(modifier: Modifier = Modifier, changeLocationClicked: () -> Unit) {
    val viewModel = koinViewModel<WeatherViewModel>()
    val hasLocationPermission by viewModel.hasLocationPermission.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val viewCreatedScope = rememberCoroutineScope()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions.any { it.value }) {
                viewModel.onLocationPermissionUpdated()
            }
        }
    )

    LaunchedEffect(viewModel) {
        if (!hasLocationPermission) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
        viewModel.onCreated(viewCreatedScope)
    }

    LazyColumn(modifier.fillMaxSize()) {
        item {
            WeatherHeaderView(
                city = uiState.city,
                modifier = modifier,
                onRequestCurrentLocation = { viewModel.requestCurrentLocation() },
                onEditClick = changeLocationClicked
            )
        }

        val weather = uiState.weather.content
        when {
            weather != null -> {
                item {
                    CurrentWeatherView(weather = weather.current)
                }
                item {
                    TodayWeatherView(hours = weather.today)
                }
                item {
                    WeekWeatherView(days = weather.week)
                }
            }

            uiState.weather.isLoading -> item {
                LoadingCurrentWeatherView()
            }

            else -> item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { viewModel.onRetryClick() }) {
                        Text(stringResource(R.string.error_retry))
                    }
                }
            }
        }
    }
}
