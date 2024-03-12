package ru.hmhub.agents.data.remote.serializables

import kotlinx.serialization.Serializable

@Serializable
data class InsertPassword(
    val id: Int,
    val password: String
)