package com.example.feature_weather_impl.data

import WeatherApi
import com.example.core_data.Data
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_api.models.WeatherSummary
import com.example.feature_weather_impl.data.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

internal class WeatherRepoImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepo {
    private val locationCache = mutableMapOf<Location, LocationDesc>()
    private val weatherCache = mutableMapOf<Location, WeatherSummary>()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun getWeather(location: Location): Flow<Data<WeatherSummary>> {
        return flow {
            val cached = weatherCache[location]
            if (cached != null) {
                emit(Data.success(cached))
                return@flow
            }

            emit(Data.loading())

            val exclude = listOf(
                "minutely",
                "alerts"
            )

            try {
                val response = withContext(ioDispatcher) {
                    weatherApi.getWeather(location.latitude, location.longitude, exclude)
                }
                if (response.isSuccess) {
                    val weather = response.getOrThrow().toDomain()
                    weatherCache[location] = weather
                    emit(Data.success(weather))
                } else {
                    throw response.exceptionOrNull() ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                emit(Data.error(e))
            }
        }
    }

    override fun getLocationDesc(location: Location): Flow<Data<LocationDesc>> {
        return flow {
            val cached = locationCache[location]
            if (cached != null) {
                emit(Data.success(cached))
                return@flow
            }

            emit(Data.loading())

            try {
                val response = withContext(ioDispatcher) {
                    weatherApi.getLocationDesc(location.latitude, location.longitude)
                }
                if (response.isSuccess) {
                    emit(Data.success(response.getOrThrow().first().toDomain()))
                } else {
                    throw response.exceptionOrNull() ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                emit(Data.error(e))
            }
        }
    }

    override fun findLocationByQuery(query: String): Flow<Data<List<LocationDesc>>> {
        return flow {
            emit(Data.loading())

            try {
                val response = withContext(ioDispatcher) {
                    weatherApi.findLocation(query)
                }
                if (response.isSuccess) {
                    emit(Data.success(response.getOrThrow().map { it.toDomain() }))
                } else {
                    throw response.exceptionOrNull() ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                emit(Data.error(e))
            }
        }
    }
}