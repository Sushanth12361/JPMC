package com.example.jpmorgantest.data.sources.search.entities

data class WeatherDetailResponse(
    val base: String,
    val clouds: CloudsResponse,
    val cod: Int,
    val coord: CoordResponse,
    val dt: Int,
    val id: Int,
    val main: MainResponse,
    val name: String,
    val sys: SysResponse,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherResponse>,
    val wind: WindResponse
)
