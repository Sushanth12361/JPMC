package com.example.jpmorgantest.data.sources.search.remote

import com.example.jpmorgantest.data.sources.search.entities.WeatherDetailResponse
import com.example.jpmorgantest.util.state.Result

interface SearchRemoteDataSource {
    suspend fun search(
        query: String? = null,
        lat: Double? = null,
        lon: Double? = null
    ): Result<WeatherDetailResponse>
}
