package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.Variables
import com.example.soundlightrgb.domain.repository.DeviceRepository
import com.example.soundlightrgb.util.value
import javax.inject.Inject

class SetModeDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mapper: Transform<Double, VariableRequest>
): UseCase<Int, DeviceResponse<Int>> {

    override suspend fun execute(params: Int?): DeviceResponse<Int> {
        val response = deviceRepository.setVariableDevice(
            Variables.MODE_VAR,
            mapper.transform(params.value().toDouble())
        )
        return DeviceResponse(
            response.isSuccessful,
            value = response.value.value().toInt(),
            errorType = response.errorType,
            errorCode = response.errorCode,
            errorMessage = response.errorMessage
        )
    }
}