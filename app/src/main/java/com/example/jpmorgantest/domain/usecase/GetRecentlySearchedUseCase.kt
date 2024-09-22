package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.domain.base.SuspendNoParamsUseCase
import com.example.jpmorgantest.util.state.Result
import javax.inject.Inject

class GetRecentlySearchedUseCase @Inject constructor(
    private val repository: SearchDataSource
) : SuspendNoParamsUseCase<String>() {
    override fun execute(): Result<String> {
        return repository.recentlySearched()
    }
}
