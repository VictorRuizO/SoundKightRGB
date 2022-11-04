package com.example.soundlightrgb.view.viewmodel

import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.ErrorType

class DeviceResponseMocks {
    val getAllVariablesDeviceResponseMocksFail : DeviceResponse<List<Pair<String, Double>>> = DeviceResponse(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )
}