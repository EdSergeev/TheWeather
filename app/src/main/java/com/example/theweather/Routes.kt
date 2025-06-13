package com.example.theweather

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

internal sealed interface Routes: NavKey {
    @Serializable
    data object Home : Routes

    @Serializable
    data object SearchCity : Routes
}