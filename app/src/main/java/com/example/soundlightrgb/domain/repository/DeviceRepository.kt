package com.example.soundlightrgb.domain.repository

import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.model.DeviceResponse

interface DeviceRepository {
    suspend fun getVariableDevice(variable: String): DeviceResponse<Double>
    suspend fun setVariableDevice(variable: String, request: VariableRequest): DeviceResponse<Double>
}