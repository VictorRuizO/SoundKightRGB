package com.example.soundlightrgb.mocks

import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.view.model.ModeItemModel
import com.example.soundlightrgb.view.model.VariableItemModel

class SetupMocks {
    val token = "112233445566yu"

    val listVariableItemModel = listOf(
        VariableItemModel(
            VariableType.POWER_VAR, "abcderfghijk"
        )
    )

    val listModeItemModel = listOf(
        ModeItemModel(
            "0",
            "solid"
        )
    )
}