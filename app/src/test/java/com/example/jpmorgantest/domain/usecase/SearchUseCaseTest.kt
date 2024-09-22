package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.util.state.Result
import com.example.jpmorgantest.util.state.isError
import com.example.jpmorgantest.util.state.isSuccess
import com.example.jpmorgantest.utils.MockkTest
import com.example.jpmorgantest.utils.data.SearchDataGenerator
import com.example.jpmorgantest.utils.data.SearchDomainGenerator
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class SearchUseCaseTest : MockkTest() {
    @RelaxedMockK
    private lateinit var repository: SearchDataSource
    lateinit var useCase: SearchByQueryUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        useCase = SearchByQueryUseCase(repository, UnconfinedTestDispatcher())
    }

    @Test
    fun execute() = runTest {
        coEvery {
            repository.searchByQuery(
                SearchDataGenerator.city
            )
        } returns Result.Success(SearchDomainGenerator.weatherDomain)

        useCase(
            SearchDataGenerator.city
        ).let {
            Assert.assertTrue(it.isSuccess)
        }
    }

    @Test
    fun executeError() = runTest {
        coEvery {
            repository.searchByQuery(
                SearchDataGenerator.city
            )
        } returns Result.Error(Exception())

        useCase(
            SearchDataGenerator.city
        ).let {
            Assert.assertTrue(it.isError)
        }
    }
}
