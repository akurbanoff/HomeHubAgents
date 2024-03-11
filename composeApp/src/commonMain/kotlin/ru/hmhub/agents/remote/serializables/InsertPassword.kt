package ru.hmhub.agents.remote.serializables

import kotlinx.serialization.Serializable

@Serializable
data class InsertPassword(
    val id: Int,
    val password: String
)