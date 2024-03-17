package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val list: List<NewsSerializable>
)

@Serializable
data class NewsSerializable(
    val id: Int,
    val title: String,
    val createdAt: String,
    val description: String,
    val photos: List<String>
)