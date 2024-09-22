package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.util.state.Result
import com.example.jpmorgantest.util.state.isError
import com.example.jpmorgantest.util.state.isSuccess
import com.example.jpmorgantest.utils.MockkTest
import com.example.jpmorgantest.utils.data.SearchDataGenerator
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetRecentlySearchedUseCaseTest : MockkTest() {
    @RelaxedMockK
    private lateinit var repository: SearchDataSource
    lateinit var useCase: GetRecentlySearchedUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        useCase = GetRecentlySearchedUseCase(repository)
    }

    @Test
    fun execute() = runTest {
        coEvery {
            repository.recentlySearched()
        } returns Result.Success(SearchDataGenerator.querySearch)

        Assert.assertTrue(useCase().isSuccess)
    }

    @Test
    fun executeError() = runTest {
        coEvery {
            repository.recentlySearched()
        } returns Result.Error(Exception())

        Assert.assertTrue(useCase().isError)
    }
}
