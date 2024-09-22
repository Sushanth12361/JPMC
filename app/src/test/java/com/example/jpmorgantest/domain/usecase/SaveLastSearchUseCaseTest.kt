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
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SaveLastSearchUseCaseTest : MockkTest() {
    @RelaxedMockK
    private lateinit var repository: SearchDataSource
    lateinit var useCase: SaveLastSearchUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        useCase = SaveLastSearchUseCase(repository, UnconfinedTestDispatcher())
    }

    @Test
    fun execute() = runTest {
        coEvery {
            repository.saveQuery(
                SearchDataGenerator.city
            )
        } returns Result.Success(Unit)

        Assert.assertTrue(
            useCase(
                SearchDataGenerator.city
            ).isSuccess
        )
    }

    @Test
    fun executeError() = runTest {
        coEvery {
            repository.saveQuery(
                SearchDataGenerator.city
            )
        } returns Result.Error(Exception())

        Assert.assertTrue(
            useCase(
                SearchDataGenerator.city
            ).isError
        )
    }
}
