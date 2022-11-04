package com.example.soundlightrgb.domain.model

data class DeviceResponse<T>(
    val isSuccessful: Boolean,
    val value: T? = null,
    val errorType: ErrorType? = null,
    val errorCode: String? = null,
    val errorMessage: String? = null,
)

enum class ErrorType{
    HTTP_ERROR,
    NETWORK_ERROR,
    GENERIC_ERROR,
}
