package com.example.jpmorgantest.data.sources.search

import com.example.jpmorgantest.data.sources.search.local.SearchLocalDataSource
import com.example.jpmorgantest.data.sources.search.mapper.SearchResultMapper
import com.example.jpmorgantest.data.sources.search.remote.SearchRemoteDataSource
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.util.constants.FORMAT_ONLY_US
import com.example.jpmorgantest.util.state.Result
import com.example.jpmorgantest.util.state.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val searchResultMapper: SearchResultMapper,
    private val searchLocalDataSource: SearchLocalDataSource
) : SearchDataSource {
    override suspend fun searchByQuery(
        query: String
    ): Result<WeatherDetail> =
        remoteDataSource.search(FORMAT_ONLY_US.format(query))
            .map(searchResultMapper::fromResponseToDomain)

    override suspend fun searchByLatLon(lat: Double, lon: Double): Result<WeatherDetail> =
        remoteDataSource.search(lat = lat, lon = lon)
            .map(searchResultMapper::fromResponseToDomain)

    override fun recentlySearched(): Result<String> {
        return searchLocalDataSource.recentlySearched()
    }

    override fun saveQuery(
        query: String
    ): Result<Unit> {
        return searchLocalDataSource.saveQuery(FORMAT_ONLY_US.format(query))
    }
}
