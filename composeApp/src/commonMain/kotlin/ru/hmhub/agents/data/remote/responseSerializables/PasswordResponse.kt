package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable

@Serializable
data class PasswordResponse(
    val status: Int
)
