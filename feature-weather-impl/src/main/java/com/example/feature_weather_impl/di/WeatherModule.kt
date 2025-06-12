package com.example.feature_weather_impl.di

import WeatherApi
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_impl.data.WeatherRepoImpl
import com.example.feature_weather_impl.service.LocationService
import com.example.feature_weather_impl.ui.WeatherUiStateMapper
import com.example.feature_weather_impl.ui.WeatherViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    factory { WeatherApi(apiKey = "f278a11b85e680d8828f6e4e36d78059") }
    singleOf(::WeatherRepoImpl) { bind<WeatherRepo>() }
    factoryOf(::WeatherUiStateMapper)
    factoryOf(::LocationService)
    viewModelOf(::WeatherViewModel)
}