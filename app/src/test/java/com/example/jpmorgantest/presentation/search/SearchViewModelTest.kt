package com.example.jpmorgantest.presentation.search

import app.cash.turbine.test
import com.example.jpmorgantest.domain.usecase.SaveLastSearchUseCase
import com.example.jpmorgantest.domain.usecase.SearchByLatLongUseCase
import com.example.jpmorgantest.domain.usecase.SearchByQueryUseCase
import com.example.jpmorgantest.presentation.deletage.LocationDelegate
import com.example.jpmorgantest.util.constants.DELAY_1000
import com.example.jpmorgantest.util.constants.EMPTY_STRING
import com.example.jpmorgantest.util.state.Result
import com.example.jpmorgantest.utils.BaseViewModelTest
import com.example.jpmorgantest.utils.data.SearchDataGenerator
import com.example.jpmorgantest.utils.data.SearchDomainGenerator
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest : BaseViewModelTest() {
    @RelaxedMockK
    private lateinit var searchedByQueryUseCase: SearchByQueryUseCase

    @RelaxedMockK
    private lateinit var searchedByLatLonUseCase: SearchByLatLongUseCase

    @RelaxedMockK
    private lateinit var saveLastSearchUseCase: SaveLastSearchUseCase

    @RelaxedMockK
    private lateinit var locationDelegate: LocationDelegate

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(
            searchedByQueryUseCase,
            searchedByLatLonUseCase,
            saveLastSearchUseCase,
            locationDelegate
        )
    }

    @Test
    fun getSearchStateUI() = runTest {
        viewModel.searchStateUI.test {
            val result = awaitItem()
            Assert.assertFalse(result.isError)
            Assert.assertFalse(result.isLoading)
            Assert.assertFalse(result.isSuccess)
            Assert.assertNull(result.detailWeather)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getTextSearch() {
        Assert.assertEquals(viewModel.textSearch.value, EMPTY_STRING)
    }

    @Test
    fun onSearchWeatherByLatLong() = runTest {
        // Mock Data
        coEvery {
            searchedByLatLonUseCase(
                SearchByLatLongUseCase.Params(
                    SearchDataGenerator.lat,
                    SearchDataGenerator.long
                )
            )
        } returns Result.Success(SearchDomainGenerator.weatherDomain)

        // Test
        viewModel.onSearchWeatherByLatLong(SearchDataGenerator.lat, SearchDataGenerator.long)
        viewModel.searchStateUI.test {
            val response = awaitItem()
            Assert.assertTrue(response.isSuccess)
            Assert.assertEquals(
                response.detailWeather?.weatherDescription,
                SearchDomainGenerator.weatherDomain.weatherDescription
            )
            Assert.assertEquals(
                response.detailWeather?.cityName,
                SearchDomainGenerator.weatherDomain.cityName
            )
            Assert.assertEquals(
                response.detailWeather?.humidity,
                SearchDomainGenerator.weatherDomain.humidity
            )
            Assert.assertEquals(
                response.detailWeather?.pressure,
                SearchDomainGenerator.weatherDomain.pressure
            )
        }
    }

    @Test
    fun onSearchWeatherByLatLongError() = runTest {
        // Mock Data
        coEvery {
            searchedByLatLonUseCase(
                SearchByLatLongUseCase.Params(
                    SearchDataGenerator.lat,
                    SearchDataGenerator.long
                )
            )
        } returns Result.Error(Exception())

        viewModel.onSearchWeatherByLatLong(SearchDataGenerator.lat, SearchDataGenerator.long)

        // Test
        viewModel.searchStateUI.test {
            val response = awaitItem()
            Assert.assertTrue(response.isError)
        }
    }

    @Test
    fun onSearchWeatherByQuery() = runTest {
        // Mock Data
        coEvery {
            searchedByQueryUseCase(
                SearchDataGenerator.city
            )
        } returns Result.Success(SearchDomainGenerator.weatherDomain)

        viewModel.onQueryChange(SearchDataGenerator.city)
        // Test
        viewModel.searchStateUI.test {
            delay(DELAY_1000)
            val response = expectMostRecentItem()
            Assert.assertTrue(response.isSuccess)
            Assert.assertEquals(
                response.detailWeather?.weatherDescription,
                SearchDomainGenerator.weatherDomain.weatherDescription
            )
            Assert.assertEquals(
                response.detailWeather?.cityName,
                SearchDomainGenerator.weatherDomain.cityName
            )
            Assert.assertEquals(
                response.detailWeather?.humidity,
                SearchDomainGenerator.weatherDomain.humidity
            )
            Assert.assertEquals(
                response.detailWeather?.pressure,
                SearchDomainGenerator.weatherDomain.pressure
            )
        }
    }

    @Test
    fun onSearchWeatherByQueryError() = runTest {
        // Mock Data
        coEvery {
            searchedByQueryUseCase(
                SearchDataGenerator.city
            )
        } returns Result.Error(Exception())

        viewModel.onQueryChange(SearchDataGenerator.city)
        // Test
        viewModel.searchStateUI.test {
            delay(DELAY_1000)
            val response = expectMostRecentItem()
            Assert.assertTrue(response.isError)
        }
    }

    @Test
    fun onSaveWeatherDetail() = runTest {
        // Mock Data
        coEvery {
            searchedByQueryUseCase(SearchDataGenerator.city)
        } returns Result.Success(SearchDomainGenerator.weatherDomain)
        viewModel.onSearchWeatherByQuery(SearchDataGenerator.city)
        coEvery {
            viewModel.saveWeatherDetail(SearchDomainGenerator.weatherDomain)
        } returns Unit

        coEvery {
            saveLastSearchUseCase(SearchDataGenerator.city)
        } returns Result.Success(Unit)
        // Run
        viewModel.onSaveWeatherDetail()

        // Test
        Assert.assertTrue(viewModel.openBottomSheet.value)
    }

    @Test
    fun getOpenBottomSheet() = runTest {
        viewModel.openBottomSheet.test {
            // For default is in false
            Assert.assertFalse(awaitItem())
            viewModel.onOpenBottomSheet()
            Assert.assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun onCloseBottomSheet() = runTest {
        viewModel.onOpenBottomSheet()
        viewModel.openBottomSheet.test {
            Assert.assertTrue(awaitItem())
            viewModel.onCloseBottomSheet()
            Assert.assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
