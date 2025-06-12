package com.example.core_data

data class Data<T>(
    val isLoading: Boolean,
    val content: T?,
    val error: Throwable?
) {
    companion object {
        fun <T> loading() = Data<T>(true, null, null)
        fun <T> success(content: T) = Data(false, content, null)
        fun <T> error(error: Throwable) = Data<T>(false, null, error)
    }
}

inline fun<R, T> Data<T>.mapContent(mapper: (T) -> R): Data<R> {
    return if (content != null) {
        Data(isLoading, mapper(content), error)
    } else {
        Data(isLoading, null, error)
    }
}