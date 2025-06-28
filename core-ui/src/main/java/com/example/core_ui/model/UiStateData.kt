package com.example.core_ui.model

import androidx.compose.runtime.Stable

@Stable
class UiData<T>(
    val isLoading: Boolean,
    val content: T?,
    val error: Throwable?
) {
    companion object {
        fun <T> loading() = UiData<T>(true, null, null)
        fun <T> success(content: T) = UiData(false, content, null)
        fun <T> error(error: Throwable) = UiData<T>(false, null, error)
    }
}