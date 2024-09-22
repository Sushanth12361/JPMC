package com.example.jpmorgantest.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmorgantest.domain.usecase.GetRecentlySearchedUseCase
import com.example.jpmorgantest.domain.usecase.SearchByQueryUseCase
import com.example.jpmorgantest.presentation.deletage.LocationDelegate
import com.example.jpmorgantest.presentation.detail.state.DetailStateUI
import com.example.jpmorgantest.util.state.getData
import com.example.jpmorgantest.util.state.isError
import com.example.jpmorgantest.util.state.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getRecentlySearchedUseCase: GetRecentlySearchedUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    locationDelegate: LocationDelegate
) : ViewModel(),
    LocationDelegate by locationDelegate {
    private val _detailStateUI = MutableStateFlow(DetailStateUI())
    val detailStateUI get() = _detailStateUI

    init {
        if (weatherDetail.value == null) {
            loadRecentlySearched()
        }
    }

    fun loadRecentlySearched() {
        viewModelScope.launch {
            _detailStateUI.update { DetailStateUI(isLoading = true) }
            val recentlySearched = async { getRecentlySearchedUseCase() }.await()
            searchByQueryUseCase(recentlySearched.getData()).let { result ->
                when {
                    result.isSuccess -> {
                        _detailStateUI.update { DetailStateUI() }
                        saveWeatherDetail(result.getData())
                    }

                    result.isError -> {
                        _detailStateUI.update { DetailStateUI(isError = false) }
                    }
                }
            }
        }
    }
}
