package com.example.feature_search_city.di

import com.example.feature_search_city.ui.SearchCityUiStateMapper
import com.example.feature_search_city.ui.SearchCityViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchCityModule = module {
    factoryOf(::SearchCityUiStateMapper)
    viewModelOf(::SearchCityViewModel)
}