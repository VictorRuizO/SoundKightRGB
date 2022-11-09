package com.example.soundlightrgb.data.mapper

import com.example.soundlightrgb.data.local.entity.ModeEntity
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.view.model.ModeItemModel
import javax.inject.Inject

class ModeItemModelsToModeDaoMapper @Inject constructor(): Transform<List<@JvmSuppressWildcards ModeItemModel>, List<@JvmSuppressWildcards ModeEntity>> {
    override fun transform(value: List<ModeItemModel>): List<ModeEntity> {
        return value.map {
            ModeEntity(it.index, it.name)
        }
    }
}