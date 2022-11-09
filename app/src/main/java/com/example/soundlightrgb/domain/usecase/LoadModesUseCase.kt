package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.view.model.ModeItemModel
import javax.inject.Inject

class LoadModesUseCase @Inject constructor(
    private val setupRepository: SetupRepository
): UseCase<Any, List<@JvmSuppressWildcards ModeItemModel>?> {
    override suspend fun execute(params: Any?): List<ModeItemModel>? {
        return setupRepository.loadModes()
    }
}