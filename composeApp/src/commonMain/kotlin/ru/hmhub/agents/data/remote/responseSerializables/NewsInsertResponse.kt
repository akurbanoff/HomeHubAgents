package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable

@Serializable
data class NewsInsertResponse(
    val status: Int
)
