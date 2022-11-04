package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.Variables
import com.example.soundlightrgb.domain.repository.DeviceRepository
import com.example.soundlightrgb.util.value
import javax.inject.Inject

class SetBrightnessDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val mapper: Transform<Double, VariableRequest>
): UseCase<Double, DeviceResponse<Double>> {

    override suspend fun execute(params: Double?): DeviceResponse<Double> {
        return deviceRepository.setVariableDevice(
            Variables.BRIGHTNESS_VAR,
            mapper.transform(params.value())
        )
    }
}