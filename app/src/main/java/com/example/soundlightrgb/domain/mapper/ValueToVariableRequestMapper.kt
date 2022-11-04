package com.example.soundlightrgb.domain.mapper

import com.example.soundlightrgb.data.remote.request.VariableRequest
import com.example.soundlightrgb.domain.generic.Transform
import javax.inject.Inject

class ValueToVariableRequestMapper @Inject constructor() : Transform<Double, VariableRequest> {
    override fun transform(value: Double): VariableRequest {
        return VariableRequest(
            value.toString()
        )
    }
}