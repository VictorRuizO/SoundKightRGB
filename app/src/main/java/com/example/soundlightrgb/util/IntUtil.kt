package com.example.soundlightrgb.util

fun Int?.value(): Int {
    this?.let { return it }
    return 0
}