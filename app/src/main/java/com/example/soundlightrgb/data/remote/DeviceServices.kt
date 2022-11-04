package com.example.soundlightrgb.data.remote

import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.data.remote.response.VariableResponse
import com.example.soundlightrgb.data.remote.response.VariableResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceServices {

    @Headers("X-Auth-Token: BBFF-sk2Xpozg4l09k6SvbWahdXT1TC2oEP")
    @GET("{variable}/values")
    suspend fun getVariableDevice(@Path("variable") variable: String): Response<VariableResponse>

    @Headers(
        "Content-Type: application/json",
        "X-Auth-Token: BBFF-sk2Xpozg4l09k6SvbWahdXT1TC2oEP"
    )
    @POST("{variable}/values")
    suspend fun setVariableDevice(@Path("variable") variable: String, @Body request: VariableRequest): Response<VariableResult>
}