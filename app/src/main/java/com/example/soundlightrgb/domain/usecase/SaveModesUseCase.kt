package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.view.model.ModeItemModel
import javax.inject.Inject

class SaveModesUseCase @Inject constructor(
    private val setupRepository: SetupRepository
): UseCase<List<@JvmSuppressWildcards ModeItemModel>, Boolean> {
    override suspend fun execute(params: List<ModeItemModel>?): Boolean {
        params?.let {
            return setupRepository.saveModes(it)
        }
        return false
    }
}