package com.example.theweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.core_ui.theme.TheWeatherTheme

internal class MainActivity : ComponentActivity() {
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