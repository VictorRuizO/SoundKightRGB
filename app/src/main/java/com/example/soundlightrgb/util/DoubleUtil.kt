package com.example.soundlightrgb.util

fun Double?.value(): Double {
    this?.let { return it }
    return 0.0
}