package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.data.memory.Variables
import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.DeviceResponseGeneric
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.domain.repository.DeviceRepository
import com.example.soundlightrgb.util.value
import javax.inject.Inject

class SetModeDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mapper: Transform<Double, VariableRequest>
): UseCase<Int, DeviceResponse<Int>> {

    override suspend fun execute(params: Int?): DeviceResponse<Int> {
        Variables.variables?.find { it.first == VariableType.MODE_VAR }?.let {
            val response = deviceRepository.setVariableDevice(
                it.second,
                mapper.transform(params.value().toDouble())
            )
            return DeviceResponse(
                response.isSuccessful,
                value = response.value?.toInt(),
                errorType = response.errorType,
                errorMessage = response.errorMessage,
                errorCode = response.errorCode
            )
        }
        return DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR_INT
    }
}