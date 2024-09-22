package com.example.jpmorgantest.data.sources.search.remote

import com.example.jpmorgantest.data.settings.network.util.BaseRemoteDataSource
import com.example.jpmorgantest.data.sources.search.entities.WeatherDetailResponse
import com.example.jpmorgantest.data.sources.search.service.SearchService
import com.example.jpmorgantest.util.state.Result
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource,
    BaseRemoteDataSource() {
    override suspend fun search(
        query: String?,
        lat: Double?,
        lon: Double?
    ): Result<WeatherDetailResponse> = getResult {
        searchService.search(query, lat, lon)
    }
}
