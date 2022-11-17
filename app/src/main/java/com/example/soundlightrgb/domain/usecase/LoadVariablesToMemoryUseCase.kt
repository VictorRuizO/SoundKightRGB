package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.data.memory.Variables
import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import javax.inject.Inject

class LoadVariablesToMemoryUseCase @Inject constructor(
    private val setupRepository: SetupRepository
): UseCase<Any, Boolean> {
    override suspend fun execute(params: Any?): Boolean {
        Variables.token = setupRepository.loadToken()
        Variables.variables = setupRepository.loadVariables()
        return !Variables.token.isNullOrEmpty() && !Variables.variables.isNullOrEmpty()
    }
}