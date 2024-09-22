package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.di.IoDispatcher
import com.example.jpmorgantest.domain.base.SuspendUseCase
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.util.state.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchByQueryUseCase @Inject constructor(
    private val repository: SearchDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<String, WeatherDetail>(
    coroutineDispatcher = dispatcher
) {
    override suspend fun execute(parameters: String): Result<WeatherDetail> {
        return repository.searchByQuery(parameters)
    }
}
