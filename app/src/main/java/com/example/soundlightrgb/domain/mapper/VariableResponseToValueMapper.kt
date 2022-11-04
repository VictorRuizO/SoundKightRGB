package com.example.soundlightrgb.domain.mapper

import com.example.soundlightrgb.data.remote.response.VariableResponse
import com.example.soundlightrgb.domain.generic.Transform
import javax.inject.Inject

class VariableResponseToValueMapper @Inject constructor(): Transform<VariableResponse?, Double?> {
    override fun transform(value: VariableResponse?): Double? {
        return value?.results?.firstOrNull()?.value
    }
}