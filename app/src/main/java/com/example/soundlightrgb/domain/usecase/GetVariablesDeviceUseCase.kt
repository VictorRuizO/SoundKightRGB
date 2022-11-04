package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.model.Variables
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.repository.DeviceRepository
import com.example.soundlightrgb.util.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetVariablesDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository,
): UseCase<Any, DeviceResponse<List<@JvmSuppressWildcards Pair<String, Double>>>> {
    override suspend fun execute(params: Any?): DeviceResponse<List<Pair<String, Double>>> {
        return withContext(Dispatchers.IO) {
            val power = async {
                deviceRepository.getVariableDevice(Variables.POWER_VAR)
            }.await()

            val brightness = async {
                deviceRepository.getVariableDevice(Variables.BRIGHTNESS_VAR)
            }.await()

            val speed = async {
                deviceRepository.getVariableDevice(Variables.SPEED_VAR)
            }.await()

            val volume = async {
                deviceRepository.getVariableDevice(Variables.VOLUME_VAR)
            }.await()

            val mode = async {
                deviceRepository.getVariableDevice(Variables.MODE_VAR)
            }.await()

            val whitePower = async {
                deviceRepository.getVariableDevice(Variables.WHITE_POWER_VAR)
            }.await()

            val whiteBrightness = async {
                deviceRepository.getVariableDevice(Variables.WHITE_BRIGHTNESS_VAR)
            }.await()

            if (power.isSuccessful && brightness.isSuccessful && speed.isSuccessful && volume.isSuccessful &&
                    mode.isSuccessful && whitePower.isSuccessful && whiteBrightness.isSuccessful) {
                val list = mutableListOf<Pair<String, Double>>()
                list.add(Pair(Variables.POWER_VAR, power.value.value()))
                list.add(Pair(Variables.BRIGHTNESS_VAR, brightness.value.value()))
                list.add(Pair(Variables.SPEED_VAR, speed.value.value()))
                list.add(Pair(Variables.VOLUME_VAR, volume.value.value()))
                list.add(Pair(Variables.MODE_VAR, mode.value.value()))
                list.add(Pair(Variables.WHITE_POWER_VAR, whitePower.value.value()))
                list.add(Pair(Variables.WHITE_BRIGHTNESS_VAR, whiteBrightness.value.value()))
                return@withContext DeviceResponse<List<Pair<String, Double>>>(
                    true,
                    list
                )
            } else {
                return@withContext DeviceResponse<List<Pair<String, Double>>>(
                    false,
                    errorMessage = "Ocurrió un error en la conexión al servidor"
                )
            }
        }
    }
}