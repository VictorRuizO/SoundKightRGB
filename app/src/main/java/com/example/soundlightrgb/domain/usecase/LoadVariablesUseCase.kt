package com.example.soundlightrgb.domain.usecase

import com.example.soundlightrgb.domain.generic.UseCase
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.view.model.VariableItemModel
import javax.inject.Inject

class LoadVariablesUseCase @Inject constructor(
    private val setupRepository: SetupRepository,
): UseCase<Any, List<@JvmSuppressWildcards VariableItemModel>?> {
    override suspend fun execute(params: Any?): List<VariableItemModel>? {
        return setupRepository.loadVariables()?.map {
            VariableItemModel(it.first, it.second)
        }
    }
}