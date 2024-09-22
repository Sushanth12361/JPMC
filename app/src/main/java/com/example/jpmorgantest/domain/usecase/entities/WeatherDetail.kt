package com.example.jpmorgantest.domain.usecase.entities

data class WeatherDetail(
    val cityName: String,
    val temperature: Double?,
    val weatherDescription: String,
    val humidity: Int?,
    val pressure: Int?,
    val windSpeed: Double?,
    val sunrise: Long,
    val sunset: Long,
    val icon: String?
)
