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

class SetWhiteBrightnessDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mapper: Transform<Double, VariableRequest>
): UseCase<Double, DeviceResponse<Double>> {

    override suspend fun execute(params: Double?): DeviceResponse<Double> {
        Variables.variables?.find { it.first == VariableType.WHITE_BRIGHTNESS_VAR }?.let {
            return deviceRepository.setVariableDevice(
                it.second,
                mapper.transform(params.value())
            )
        }
        return DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
    }
}