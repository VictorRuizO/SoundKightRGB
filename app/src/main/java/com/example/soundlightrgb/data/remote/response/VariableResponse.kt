package com.example.soundlightrgb.data.remote.response

data class VariableResponse(
    val count: Boolean?,
    val next: String?,
    val previous: String?,
    val results: List<VariableResult>,
)

data class VariableResult(
    val timestamp: String?,
    val value: Double?,
    val created_at: String?,
)
