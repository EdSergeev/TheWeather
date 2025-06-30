package com.example.feature_search_city.ui

import app.cash.turbine.test
import com.example.core_data.Data
import com.example.core_ui.model.UiData
import com.example.feature_search_city.ui.SearchCityUiApi.DomainState
import com.example.feature_search_city.ui.SearchCityUiApi.UiState
import com.example.feature_weather_api.LocationRepo
import com.example.feature_weather_api.WeatherRepo
import com.example.feature_weather_api.models.Location
import com.example.feature_weather_api.models.LocationDesc
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCityViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val locationRepo = mockk<LocationRepo>(relaxed = true)
    private val weatherRepo = mockk<WeatherRepo>()
    private val uiStateMapper = mockk<SearchCityUiStateMapper>()

    private lateinit var viewModel: SearchCityViewModel
    private val testScope = TestScope(testDispatcher)

    private val mockLocation = Location(40.7128, -74.0060)
    private val mockLocationDesc = LocationDesc(
        city = "New York",
        state = "New York",
        location = mockLocation
    )

    @Before
    fun setup() {
        // Setup default mapper behavior
        every { uiStateMapper.mapState(any()) } returns UiState(
            query = "",
            cities = UiData.success(emptyList()),
            showEmptyResult = false
        )

        viewModel = SearchCityViewModel(
            locationRepo = locationRepo,
            uiStateMapper = uiStateMapper,
            weatherRepo = weatherRepo,
        )
    }

    @Test
    fun `initial state should have empty query and cities`() = runTest {
        // Given
        val expectedDomainState = DomainState(
            query = "",
            cities = Data.success(emptyList())
        )
        val expectedUiState = UiState(
            query = "",
            cities = UiData(content = emptyList(), isLoading = false, error = null),
            showEmptyResult = false
        )

        every { uiStateMapper.mapState(expectedDomainState) } returns expectedUiState

        // When & Then
        viewModel.uiState.test {
            assertEquals(expectedUiState, awaitItem())
        }

        verify { uiStateMapper.mapState(expectedDomainState) }
    }

    @Test
    fun `onQueryChange should update domain state and emit to query flow`() = runTest {
        // Given
        val query = "New York"
        val initialDomainState = DomainState(
            query = "",
            cities = Data.success(emptyList())
        )
        val initialUiState = UiState(
            query = "",
            cities = UiData.success(content = emptyList()),
            showEmptyResult = false
        )
        val updatedDomainState = DomainState(
            query = query,
            cities = Data.success(emptyList())
        )
        val expectedUiState = UiState(
            query = query,
            cities = UiData.success(content = emptyList()),
            showEmptyResult = false
        )

        every { uiStateMapper.mapState(initialDomainState) } returns initialUiState
        every { uiStateMapper.mapState(updatedDomainState) } returns expectedUiState

        // When
        viewModel.onCreated(testScope)
        viewModel.onQueryChange(query)

        // Then
        viewModel.uiState.test {
            assertEquals(initialUiState, awaitItem())
            assertEquals(expectedUiState, awaitItem())
        }
        verify { uiStateMapper.mapState(updatedDomainState) }

    }

    @Test
    fun `onQueryChange should not update if query is the same`() = runTest {
        every { weatherRepo.findLocationByQuery(any()) } returns flowOf(Data.loading())
        viewModel.onCreated(testScope)

        // Given
        val query = "New York"
        viewModel.onQueryChange(query)
        testScope.advanceTimeBy(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS + 1)

        // When
        viewModel.onQueryChange(query) // Same query again
        testScope.advanceTimeBy(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS + 1)

        viewModel.uiState.test {
            awaitItem()
        }
        verify(exactly = 1) { weatherRepo.findLocationByQuery(any()) }
    }

    @Test
    fun `onCreated should debounce query changes`() = runTest {
        // Given
        val query1 = "New"
        val query2 = "New York"
        val cities = listOf(mockLocationDesc)
        val citiesData = Data.success(cities)

        every { weatherRepo.findLocationByQuery(query2) } returns flowOf(citiesData)

        // When
        viewModel.onCreated(testScope)
        viewModel.onQueryChange(query1)
        viewModel.onQueryChange(query2)

        // Advance time less than debounce period
        testScope.advanceTimeBy(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS - 100)

        // Then - should not have called weatherRepo yet
        verify(exactly = 0) { weatherRepo.findLocationByQuery(any()) }

        // When - advance past debounce period
        testScope.advanceTimeBy(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS + 100)

        // Then - should only call with the latest query
        verify(exactly = 1) { weatherRepo.findLocationByQuery(query2) }
        verify(exactly = 0) { weatherRepo.findLocationByQuery(query1) }
    }

    @Test
    fun `onCityClicked should store location in repository`() {
        // Given
        val locationDesc = mockLocationDesc

        // When
        viewModel.onCityClicked(locationDesc)

        // Then
        verify { locationRepo.storeLocation(locationDesc.location) }
    }

    @Test
    fun `should handle loading state correctly`() = runTest {
        // Given
        val query = "London"
        val loadingData = Data.loading<List<LocationDesc>>()

        every { weatherRepo.findLocationByQuery(query) } returns flowOf(loadingData)

        val expectedDomainState = DomainState(
            query = query,
            cities = loadingData
        )
        val expectedUiState = UiState(
            query = query,
            cities = UiData.loading(),
            showEmptyResult = false
        )

        every { uiStateMapper.mapState(expectedDomainState) } returns expectedUiState

        // When
        viewModel.onCreated(testScope)
        viewModel.onQueryChange(query)
        testScope.advanceTimeBy(SearchCityUiApi.QUERY_DEBOUNCE_MILLIS + 100)

        // Then
        viewModel.uiState.test {
            awaitItem()// initial state
            assertEquals(expectedUiState, awaitItem())
        }
    }
}