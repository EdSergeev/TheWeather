package com.example.theweather

import android.app.Application
import com.example.feature_search_city.di.searchCityModule
import com.example.feature_weather_impl.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

internal class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApp)
            modules(
                weatherModule,
                searchCityModule,
            )
        }
    }
}