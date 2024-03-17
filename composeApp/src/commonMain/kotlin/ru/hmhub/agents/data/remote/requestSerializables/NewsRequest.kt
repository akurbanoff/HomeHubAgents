package ru.hmhub.agents.data.remote.requestSerializables

import kotlinx.serialization.Serializable

@Serializable
data class NewsRequest(
    val title: String,
    val description: String,
    val photos: List<String>
)