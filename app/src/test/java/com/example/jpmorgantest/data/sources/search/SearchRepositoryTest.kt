package com.example.jpmorgantest.data.sources.search

import com.example.jpmorgantest.data.sources.search.local.SearchLocalDataSource
import com.example.jpmorgantest.data.sources.search.mapper.SearchResultMapper
import com.example.jpmorgantest.data.sources.search.remote.SearchRemoteDataSource
import com.example.jpmorgantest.util.constants.FORMAT_ONLY_US
import com.example.jpmorgantest.util.constants.ONE
import com.example.jpmorgantest.util.state.Result
import com.example.jpmorgantest.util.state.dataOrNull
import com.example.jpmorgantest.util.state.isError
import com.example.jpmorgantest.util.state.isSuccess
import com.example.jpmorgantest.utils.MockkTest
import com.example.jpmorgantest.utils.data.SearchDataGenerator
import com.example.jpmorgantest.utils.data.SearchDomainGenerator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchRepositoryTest : MockkTest() {
    private lateinit var searchDataSource: SearchDataSource

    @RelaxedMockK
    private lateinit var remoteDataSource: SearchRemoteDataSource

    @RelaxedMockK
    private lateinit var searchResultMapper: SearchResultMapper

    @RelaxedMockK
    private lateinit var searchLocalDataSource: SearchLocalDataSource

    @Before
    fun setUp() {
        searchDataSource =
            SearchRepository(remoteDataSource, searchResultMapper, searchLocalDataSource)
    }

    @Test
    fun searchFilterSuccess() = runTest {
        coEvery {
            remoteDataSource.search(
                FORMAT_ONLY_US.format(SearchDataGenerator.city)
            )
        } returns Result.Success(SearchDataGenerator.weatherData)

        every {
            searchResultMapper.fromResponseToDomain(SearchDataGenerator.weatherData)
        } returns SearchDomainGenerator.weatherDomain
        searchDataSource.searchByQuery(
            SearchDataGenerator.city
        ).let {
            Assert.assertEquals(it.dataOrNull(), SearchDomainGenerator.weatherDomain)
            Assert.assertTrue(it.isSuccess)
        }
    }

    @Test
    fun searchFilterError() = runTest {
        coEvery {
            remoteDataSource.search(
                FORMAT_ONLY_US.format(SearchDataGenerator.city)
            )
        } returns Result.Error(Exception())

        searchDataSource.searchByQuery(
            SearchDataGenerator.city
        ).let {
            Assert.assertTrue(it.isError)
        }
    }

    @Test
    fun recentlySearched() = runTest {
        coEvery { searchLocalDataSource.recentlySearched() } returns Result.Success(
            SearchDataGenerator.city
        )

        searchDataSource.recentlySearched().let {
            Assert.assertTrue(it.isSuccess)
            Assert.assertEquals(it.dataOrNull(), SearchDataGenerator.city)
        }
        // Check verify
        coVerify(exactly = ONE) {
            searchLocalDataSource.recentlySearched()
        }
    }

    @Test
    fun recentlySearchedError() = runTest {
        coEvery { searchLocalDataSource.recentlySearched() } returns Result.Error(Exception())

        searchDataSource.recentlySearched().let {
            Assert.assertTrue(it.isError)
        }
        // Check verify
        coVerify(exactly = ONE) {
            searchLocalDataSource.recentlySearched()
        }
    }

    @Test
    fun saveQuery() = runTest {
        coEvery {
            searchLocalDataSource.saveQuery(
                FORMAT_ONLY_US.format(SearchDataGenerator.city)
            )
        } returns Result.Success(Unit)

        searchDataSource.saveQuery(
            SearchDataGenerator.city
        ).let {
            Assert.assertTrue(it.isSuccess)
        }
    }

    @Test
    fun saveQueryError() = runTest {
        coEvery {
            searchLocalDataSource.saveQuery(
                FORMAT_ONLY_US.format(SearchDataGenerator.city)
            )
        } returns Result.Error(Exception())

        searchDataSource.saveQuery(
            SearchDataGenerator.city
        ).let {
            Assert.assertTrue(it.isError)
        }
    }
}
