package com.example.jpmorgantest.data.settings.network.util.error

sealed class ErrorSource : Exception() {

    object Network : ErrorSource()

    object Unknown : ErrorSource()

    data class ServiceError(
        val errorCode: String,
        val errorMessage: String?
    ) : ErrorSource()
}
