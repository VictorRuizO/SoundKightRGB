package com.example.soundlightrgb.view.model

import com.example.soundlightrgb.domain.model.VariableType

data class VariableItemModel(
    val type: VariableType,
    var value: String,
)
