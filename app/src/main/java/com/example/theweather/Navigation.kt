package com.example.theweather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.feature_search_city.ui.SearchCityScreen
import com.example.feature_weather_impl.ui.WeatherScreen

@Composable
internal fun Navigation(modifier: Modifier) {
    val backStack = rememberNavBackStack(Routes.Home)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Routes.Home -> NavEntry(key) {
                    WeatherScreen(modifier) {
                        backStack.add(Routes.SearchCity)
                    }
                }

                is Routes.SearchCity -> NavEntry(key) {
                    SearchCityScreen(modifier) {
                        backStack.removeLastOrNull()
                    }
                }

                else -> error("Unknown route $key")
            }
        }
    )
}