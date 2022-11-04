package com.example.soundlightrgb.domain.generic

interface UseCase<Params, T> {
    suspend fun execute(params: Params? = null): T
}