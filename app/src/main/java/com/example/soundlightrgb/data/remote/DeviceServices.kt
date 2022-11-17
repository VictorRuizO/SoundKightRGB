package com.example.soundlightrgb.data.remote

import com.example.soundlightrgb.data.memory.Variables
import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.data.remote.response.VariableResponse
import com.example.soundlightrgb.data.remote.response.VariableResult
import com.example.soundlightrgb.data.respository.DeviceRepositoryImp
import com.example.soundlightrgb.util.value
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceServices {

    @GET("{variable}/values")
    suspend fun getVariableDevice(
        @Path("variable") variable: String,
        @Header("X-Auth-Token") token: String = Variables.token.value(),
    ): Response<VariableResponse>

    @POST("{variable}/values")
    suspend fun setVariableDevice(
        @Path("variable") variable: String,
        @Body request: VariableRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("X-Auth-Token") token: String = Variables.token.value(),
    ): Response<VariableResult>
}