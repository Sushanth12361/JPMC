package com.example.jpmorgantest.domain.usecase

import com.example.jpmorgantest.data.sources.search.SearchDataSource
import com.example.jpmorgantest.di.IoDispatcher
import com.example.jpmorgantest.domain.base.SuspendUseCase
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.util.state.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchByLatLongUseCase @Inject constructor(
    private val repository: SearchDataSource,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : SuspendUseCase<SearchByLatLongUseCase.Params, WeatherDetail>(
    coroutineDispatcher = dispatcher
) {
    data class Params(
        val lat: Double,
        val long: Double
    )

    override suspend fun execute(parameters: Params): Result<WeatherDetail> {
        return repository.searchByLatLon(parameters.lat, parameters.long)
    }
}
