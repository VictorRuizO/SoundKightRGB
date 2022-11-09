package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.util.value
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val setupRepository: SetupRepository
): UseCase<String, Boolean> {
    override suspend fun execute(params: String?): Boolean {
        return setupRepository.saveToken(params.value())
    }
}