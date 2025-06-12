package com.example.feature_weather_impl.data

import WeatherApi
import com.example.core_data.Data
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import com.example.feature_weather_api.models.WeatherSummary
import com.example.feature_weather_impl.data.mappers.toDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class WeatherRepoImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepo {
    private val locationCache = mutableMapOf<Location, LocationDesc>()

    override fun getWeather(location: Location): Flow<Data<WeatherSummary>> {
        return flow {
            emit(Data.loading())

            val response = weatherApi.getWeather(location.latitude, location.longitude)
            if (response.isSuccess) {
//                emit(Data.success(response.getOrNull()))
            } else {
                emit(Data.error(response.exceptionOrNull() ?: Exception("Unknown error")))
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

            val response = weatherApi.getLocationDesc(location.latitude, location.longitude)

            //todo: don't commit
            delay(2000)

            if (response.isSuccess) {
                emit(Data.success(response.getOrThrow().first().toDomain()))
            } else {
                emit(Data.error(response.exceptionOrNull() ?: Exception("Unknown error")))
            }
        }
    }

    override fun findLocationByQuery(query: String): Flow<Data<List<LocationDesc>>> {
        TODO("Not yet implemented")
    }
}