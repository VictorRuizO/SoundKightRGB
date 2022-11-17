package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.data.memory.Variables
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.DeviceResponseGeneric
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.domain.repository.DeviceRepository
import com.example.soundlightrgb.util.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetVariablesDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
): UseCase<Any, DeviceResponse<List<@JvmSuppressWildcards Pair<VariableType, Double>>>> {
    override suspend fun execute(params: Any?): DeviceResponse<List<Pair<VariableType, Double>>> {
        return withContext(Dispatchers.IO) {
            val power = async {
                Variables.variables?.find { it.first == VariableType.POWER_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val brightness = async {
                Variables.variables?.find { it.first == VariableType.BRIGHTNESS_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val speed = async {
                Variables.variables?.find { it.first == VariableType.SPEED_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val volume = async {
                Variables.variables?.find { it.first == VariableType.VOLUME_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val mode = async {
                Variables.variables?.find { it.first == VariableType.MODE_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val whitePower = async {
                Variables.variables?.find { it.first == VariableType.WHITE_POWER_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            val whiteBrightness = async {
                Variables.variables?.find { it.first == VariableType.WHITE_BRIGHTNESS_VAR }?.let {
                    return@async deviceRepository.getVariableDevice(it.second)
                }
                return@async DeviceResponseGeneric.DEVICE_RESPONSE_NO_VAR_ERROR
            }.await()

            if (power.isSuccessful && brightness.isSuccessful && speed.isSuccessful && volume.isSuccessful &&
                    mode.isSuccessful && whitePower.isSuccessful && whiteBrightness.isSuccessful) {
                val list = mutableListOf<Pair<VariableType, Double>>()
                list.add(Pair(VariableType.POWER_VAR, power.value.value()))
                list.add(Pair(VariableType.BRIGHTNESS_VAR, brightness.value.value()))
                list.add(Pair(VariableType.SPEED_VAR, speed.value.value()))
                list.add(Pair(VariableType.VOLUME_VAR, volume.value.value()))
                list.add(Pair(VariableType.MODE_VAR, mode.value.value()))
                list.add(Pair(VariableType.WHITE_POWER_VAR, whitePower.value.value()))
                list.add(Pair(VariableType.WHITE_BRIGHTNESS_VAR, whiteBrightness.value.value()))
                return@withContext DeviceResponse<List<Pair<VariableType, Double>>>(
                    true,
                    list
                )
            } else {
                return@withContext DeviceResponse<List<Pair<VariableType, Double>>>(
                    false,
                    errorMessage = "Ocurrió un error en la conexión al servidor"
                )
            }
        }
    }
}