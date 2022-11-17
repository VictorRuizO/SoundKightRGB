package com.example.soundlightrgb.domain.mapper

import com.example.soundlightrgb.mocks.VariableResponseMocks
import org.junit.Assert.*

import org.junit.Test

class VariableResponseToValueMapperTest {

    private val mapper = VariableResponseToValueMapper()
    private val mocks = VariableResponseMocks()

    @Test
    fun `Test transform`() {
        // Arrange

        //Act
        val result = mapper.transform(mocks.variableResponse)

        //Assert
        assertTrue(result != null)
        assertEquals(mocks.variableResponse.results.first()?.value, result)
    }
}