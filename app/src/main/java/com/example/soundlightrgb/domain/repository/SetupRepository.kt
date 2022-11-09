package com.example.soundlightrgb.domain.repository

import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.view.model.ModeItemModel

interface SetupRepository {
    suspend fun saveToken(value: String): Boolean
    suspend fun loadToken(): String?
    suspend fun saveVariables(vars: List<Pair<VariableType, String>>): Boolean
    suspend fun loadVariables(): List<Pair<VariableType, String>>?
    suspend fun saveModes(modes: List<ModeItemModel>): Boolean
    suspend fun loadModes(): List<ModeItemModel>?
}