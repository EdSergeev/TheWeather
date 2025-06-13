package com.example.theweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.core_ui.theme.TheWeatherTheme
import com.example.feature_search_city.ui.SearchCityScreen
import com.example.feature_weather_impl.ui.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier) {
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