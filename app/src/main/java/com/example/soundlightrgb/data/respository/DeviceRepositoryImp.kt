package com.example.soundlightrgb.data.respository

import com.example.soundlightrgb.data.remote.DeviceServices
import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.model.DeviceResponse
import com.example.soundlightrgb.domain.model.ErrorType
import com.example.soundlightrgb.domain.repository.DeviceRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeviceRepositoryImp @Inject constructor(
    private val deviceServices: DeviceServices
): DeviceRepository {
    override suspend fun getVariableDevice(variable: String): DeviceResponse<Double> {
        return try {
            val response = deviceServices.getVariableDevice(variable)
            if (response.isSuccessful) {
                DeviceResponse(
                    true,
                    response.body()?.results?.firstOrNull()?.value
                )
            } else {
                DeviceResponse(
                    false,
                    errorType = ErrorType.GENERIC_ERROR,
                    errorCode = response.code().toString(),
                    errorMessage = response.message()
                )
            }
        } catch (e: HttpException) {
            DeviceResponse(
                false,
                errorType = ErrorType.HTTP_ERROR,
                errorCode = e.code().toString(),
                errorMessage = e.message()
            )
        } catch (e: IOException) {
            DeviceResponse(
                false,
                errorType = ErrorType.NETWORK_ERROR,
                errorMessage = e.message
            )
        } catch (e: Exception) {
            DeviceResponse(
                false,
                errorType = ErrorType.GENERIC_ERROR,
                errorMessage = e.message
            )
        }
    }

    override suspend fun setVariableDevice(variable: String, request: VariableRequest): DeviceResponse<Double> {
        return try {
            val response = deviceServices.setVariableDevice(variable, request)
            if (response.isSuccessful) {
                DeviceResponse(
                    true,
                    response.body()?.value
                )
            } else {
                DeviceResponse(
                    false,
                    errorType = ErrorType.GENERIC_ERROR,
                    errorCode = response.code().toString(),
                    errorMessage = response.message()
                )
            }
        } catch (e: HttpException) {
            DeviceResponse(
                false,
                errorType = ErrorType.HTTP_ERROR,
                errorCode = e.code().toString(),
                errorMessage = e.message()
            )
        } catch (e: IOException) {
            DeviceResponse(
                false,
                errorType = ErrorType.NETWORK_ERROR,
                errorMessage = e.message
            )
        } catch (e: Exception) {
            DeviceResponse(
                false,
                errorType = ErrorType.GENERIC_ERROR,
                errorMessage = e.message
            )
        }
    }
}