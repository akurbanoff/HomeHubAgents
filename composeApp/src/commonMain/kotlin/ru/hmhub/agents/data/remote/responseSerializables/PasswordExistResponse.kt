package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable

@Serializable
data class PasswordExistResponse(
    val isExist: Boolean
)
