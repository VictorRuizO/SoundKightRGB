package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.model.Variables
import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.repository.DeviceRepository
import javax.inject.Inject

class SetPowerDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mapper: Transform<Double, VariableRequest>
): UseCase<Boolean, DeviceResponse<Boolean>> {

    private val ON = 1.00
    private val OFF = 0.00

    override suspend fun execute(params: Boolean?): DeviceResponse<Boolean> {
        val value = if (params == true) ON else OFF
        val response =  deviceRepository.setVariableDevice(
            Variables.POWER_VAR,
            mapper.transform(value)
        )
        return DeviceResponse(
            response.isSuccessful,
            value = response.value == 1.0,
            errorCode = response.errorCode,
            errorMessage = response.errorMessage,
            errorType = response.errorType,
        )

    }
}