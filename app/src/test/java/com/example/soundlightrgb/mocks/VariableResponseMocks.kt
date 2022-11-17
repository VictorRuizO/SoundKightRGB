package com.example.soundlightrgb.mocks

import com.example.soundlightrgb.data.remote.response.VariableResponse
import com.example.soundlightrgb.data.remote.response.VariableResult

class VariableResponseMocks {
    val variableResponse = VariableResponse(
        true,
        null,
        null,
        listOf(
            VariableResult(
                null,
                250.0,
                "27-oct-2022"
            )
        )
    )
}