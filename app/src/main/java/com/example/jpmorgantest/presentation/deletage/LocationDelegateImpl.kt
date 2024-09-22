package com.example.jpmorgantest.presentation.deletage

import com.example.jpmorgantest.di.MainDispatcher
import com.example.jpmorgantest.domain.usecase.entities.WeatherDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LocationDelegateImpl @Inject constructor(
    @MainDispatcher coroutineDispatcher: CoroutineDispatcher
) : LocationDelegate,
    CoroutineScope by CoroutineScope(coroutineDispatcher) {

    private val _weatherDetail: MutableStateFlow<WeatherDetail?> = MutableStateFlow(null)
    override val weatherDetail: StateFlow<WeatherDetail?>
        get() = _weatherDetail

    override fun saveWeatherDetail(detail: WeatherDetail) {
        _weatherDetail.update { detail }
    }
}
