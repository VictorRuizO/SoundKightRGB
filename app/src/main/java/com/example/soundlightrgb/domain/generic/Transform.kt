package com.example.soundlightrgb.domain.generic

interface Transform<T1, T2> {
    fun transform(value: T1): T2
}