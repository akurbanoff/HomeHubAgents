package ru.hmhub.agents.data.remote.responseSerializables


import kotlinx.serialization.Serializable
import ru.hmhub.agents.data.remote.serializables.Dealing

@Serializable
data class DealingResponse(
    val list: List<Dealing>
)
