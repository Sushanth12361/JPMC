package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.di.IoDispatcher
import com.example.jpmorgantest.domain.base.SuspendUseCase
import com.example.jpmorgantest.util.state.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveLastSearchUseCase @Inject constructor(
    private val repository: SearchDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<String, Unit>(
    coroutineDispatcher = dispatcher
) {
    override suspend fun execute(parameters: String): Result<Unit> {
        return repository.saveQuery(parameters)
    }
}
