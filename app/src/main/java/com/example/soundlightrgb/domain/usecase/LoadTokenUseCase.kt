package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import javax.inject.Inject

class LoadTokenUseCase @Inject constructor(
    private val setupRepository: SetupRepository
): UseCase<Any, String?> {
    override suspend fun execute(params: Any?): String? {
        return setupRepository.loadToken()
    }
}