package com.example.soundlightrgb.domain.mapper

import com.example.soundlightrgb.mocks.VariableRequestMocks
import com.example.soundlightrgb.mocks.VariableResponseMocks
import org.junit.Assert.*

import org.junit.Test

class ValueToVariableRequestMapperTest {

    private val mapper = ValueToVariableRequestMapper()
    private val mocks = VariableRequestMocks()

    @Test
    fun `Test transform`() {
        // Arrange
        val value = 250.0

        //Act
        val result = mapper.transform(value)

        //Assert
        assertEquals(mocks.variableRequest, result)
    }
}