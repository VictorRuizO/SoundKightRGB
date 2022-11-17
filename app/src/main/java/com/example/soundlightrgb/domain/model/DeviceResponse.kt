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

object DeviceResponseGeneric {
    val DEVICE_RESPONSE_NO_VAR_ERROR = DeviceResponse<Double>(
        isSuccessful = false,
        value = null,
        errorMessage = "No hay variable guardada.",
        errorCode = "-1"
    )
    val DEVICE_RESPONSE_NO_VAR_ERROR_INT = DeviceResponse<Int>(
        isSuccessful = false,
        value = null,
        errorMessage = "No hay variable guardada.",
        errorCode = "-1"
    )

    val DEVICE_RESPONSE_NO_VAR_ERROR_BOOL = DeviceResponse<Boolean>(
        isSuccessful = false,
        value = null,
        errorMessage = "No hay variable guardada.",
        errorCode = "-1"
    )
}
