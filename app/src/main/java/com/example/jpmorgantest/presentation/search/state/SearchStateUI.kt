package com.example.jpmorgantest.presentation.search.state

import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail

data class SearchStateUI(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var isSuccess: Boolean = false,
    var detailWeather: WeatherDetail? = null
)
