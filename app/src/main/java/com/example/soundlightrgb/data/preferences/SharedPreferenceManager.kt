package com.example.soundlightrgb.data.preferences

import android.content.Context
import com.example.soundlightrgb.util.EMPTY_STRING
import com.example.soundlightrgb.util.value
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sp = context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE)

    fun saveString(id: String, value: String): Boolean = with(sp.edit()) {
        putString(id, value)
        apply()
        return@with true
    }

    fun loadString(id: String): String {
        return sp.getString(id, EMPTY_STRING).value()
    }

    companion object {
        const val SHARE_NAME = "SET_UP"
    }
}