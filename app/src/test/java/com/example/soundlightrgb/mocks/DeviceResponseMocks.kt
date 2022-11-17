package com.example.soundlightrgb.mocks

import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.ErrorType
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.view.model.ModeItemModel

class DeviceResponseMocks {
    val getAllVariablesDeviceResponseMocksFail : DeviceResponse<List<Pair<VariableType, Double>>> = DeviceResponse(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val getAllVariablesDeviceResponseMocks : DeviceResponse<List<Pair<VariableType, Double>>> = DeviceResponse(
        isSuccessful = true,
        value = listOf(
            Pair(VariableType.POWER_VAR, 1.0),
            Pair(VariableType.BRIGHTNESS_VAR, 1.0),
            Pair(VariableType.SPEED_VAR, 1.0),
            Pair(VariableType.VOLUME_VAR, 1.0),
            Pair(VariableType.MODE_VAR, 1.0),
            Pair(VariableType.WHITE_POWER_VAR, 1.0),
            Pair(VariableType.WHITE_BRIGHTNESS_VAR, 1.0),
        )
    )

    val setPowerDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = true
    )

    val setPowerDeviceMockFail = DeviceResponse<Boolean>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setColorDeviceMock = DeviceResponse<Int>(
        isSuccessful = true,
        value = 168972667
    )

    val setColorDeviceMockFail = DeviceResponse<Int>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setBrightnessDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = 100.0
    )

    val setBrightnessDeviceMockFail = DeviceResponse<Double>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setSpeedDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = 100.0
    )

    val setSpeedDeviceMockFail = DeviceResponse<Double>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setvolumeDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = 100.0
    )

    val setVolumeDeviceMockFail = DeviceResponse<Double>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setModeDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = 1
    )

    val setModeDeviceMockFail = DeviceResponse<Int>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setWhitePowerDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = false
    )

    val setWhitePowerDeviceMockFail = DeviceResponse<Boolean>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val setWhiteBrightnessDeviceMock = DeviceResponse(
        isSuccessful = true,
        value = 200.0
    )

    val setWhiteBrightnessDeviceMockFail = DeviceResponse<Double>(
        isSuccessful = false,
        errorMessage = "Error al conectarse al servidor",
        errorType = ErrorType.GENERIC_ERROR,
        errorCode = "200"
    )

    val getModesSaved = listOf(
        ModeItemModel("0", "solid"),
        ModeItemModel("1", "rainbow"),
    )
}