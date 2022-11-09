package com.example.soundlightrgb.util

import android.text.Editable

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun String?.value(): String {
    this?.let { return it}
    return EMPTY_STRING
}

const val EMPTY_STRING = ""