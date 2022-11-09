package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.view.model.VariableItemModel
import javax.inject.Inject

class SaveVariableSetupDataUseCase @Inject constructor(
    private val setupRepository: SetupRepository,
): UseCase<List<@JvmSuppressWildcards VariableItemModel>, Boolean> {
    override suspend fun execute(params: List<VariableItemModel>?): Boolean {
        params?.let {
            return  setupRepository.saveVariables(
                it.map {
                    Pair(it.type, it.value)
                }
            )
        }
        return false
    }
}