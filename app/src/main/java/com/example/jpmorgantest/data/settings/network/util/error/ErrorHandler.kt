package com.example.jpmorgantest.data.settings.network.util.error

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorSource
}
