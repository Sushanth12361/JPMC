package com.example.jpmorgantest.data.sources.search

import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import com.example.jpmorgantest.util.state.Result

interface SearchDataSource {
    suspend fun searchByQuery(
        query: String
    ): Result<WeatherDetail>

    suspend fun searchByLatLon(
        lat: Double,
        lon: Double
    ): Result<WeatherDetail>

    fun recentlySearched(): Result<String>
    fun saveQuery(
        query: String
    ): Result<Unit>
}
