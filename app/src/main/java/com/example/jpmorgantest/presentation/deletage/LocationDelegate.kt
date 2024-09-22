package com.example.jpmorgantest.presentation.deletage

import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import kotlinx.coroutines.flow.StateFlow

interface LocationDelegate {

    val weatherDetail: StateFlow<WeatherDetail?>
    fun saveWeatherDetail(detail: WeatherDetail)
}
