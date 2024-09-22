package com.example.jpmorgantest.data.sources.search.mapper

import com.example.jpmorgantest.data.sources.search.entities.WeatherDetailResponse
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail

interface SearchResultMapper {
    fun fromResponseToDomain(info: WeatherDetailResponse): WeatherDetail
}
