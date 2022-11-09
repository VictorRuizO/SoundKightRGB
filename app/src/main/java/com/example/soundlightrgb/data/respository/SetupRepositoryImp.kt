package com.example.soundlightrgb.data.respository

import com.example.soundlightrgb.data.local.dao.ModeDAO
import com.example.soundlightrgb.data.local.entity.ModeEntity
import com.example.soundlightrgb.data.preferences.SharedPreferenceManager
import com.example.soundlightrgb.domain.generic.Transform
import com.example.soundlightrgb.domain.model.VariableType
import com.example.soundlightrgb.domain.repository.SetupRepository
import com.example.soundlightrgb.util.EMPTY_STRING
import com.example.soundlightrgb.view.model.ModeItemModel
import javax.inject.Inject

class SetupRepositoryImp @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val modeDAO: ModeDAO,
    private val modelItemModeMapper: Transform<List<@JvmSuppressWildcards ModeItemModel>, List<@JvmSuppressWildcards ModeEntity>>
): SetupRepository {
    override suspend fun saveToken(value: String): Boolean {
        return sharedPreferenceManager.saveString(TOKEN_ID, value)
    }

    override suspend fun loadToken(): String? {
        return sharedPreferenceManager.loadString(TOKEN_ID)
    }

    override suspend fun saveVariables(vars: List<Pair<VariableType, String>>): Boolean {
        vars.forEach {
            sharedPreferenceManager.saveString(it.first.toString(), it.second)
        }
        return true
    }

    override suspend fun loadVariables(): List<Pair<VariableType, String>>? {
        val vars = listOf(
            Pair(VariableType.POWER_VAR, sharedPreferenceManager.loadString(VariableType.POWER_VAR.toString())),
            Pair(VariableType.COLOR_VAR, sharedPreferenceManager.loadString(VariableType.COLOR_VAR.toString())),
            Pair(VariableType.BRIGHTNESS_VAR, sharedPreferenceManager.loadString(VariableType.BRIGHTNESS_VAR.toString())),
            Pair(VariableType.SPEED_VAR, sharedPreferenceManager.loadString(VariableType.SPEED_VAR.toString())),
            Pair(VariableType.VOLUME_VAR, sharedPreferenceManager.loadString(VariableType.VOLUME_VAR.toString())),
            Pair(VariableType.MODE_VAR, sharedPreferenceManager.loadString(VariableType.MODE_VAR.toString())),
            Pair(VariableType.WHITE_POWER_VAR, sharedPreferenceManager.loadString(VariableType.WHITE_POWER_VAR.toString())),
            Pair(VariableType.WHITE_BRIGHTNESS_VAR, sharedPreferenceManager.loadString(VariableType.WHITE_BRIGHTNESS_VAR.toString())),
        )

        return if (vars.filter { it.second != EMPTY_STRING }.size == vars.size){
            vars
        } else {
            null
        }
    }

    override suspend fun saveModes(modes: List<ModeItemModel>): Boolean {
        modeDAO.insertModes(
            modelItemModeMapper.transform(modes)
        )
        return true
    }

    override suspend fun loadModes(): List<ModeItemModel>? {
        return modeDAO.getModes().map {
            ModeItemModel(it.id, it.value)
        }
    }


    companion object {
        private val TOKEN_ID = "TOKEN_ID"
    }
}