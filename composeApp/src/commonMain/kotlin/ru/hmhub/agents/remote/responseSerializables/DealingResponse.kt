package ru.hmhub.agents.remote.responseSerializables


import kotlinx.serialization.Serializable
import ru.hmhub.agents.remote.serializables.Dealing

@Serializable
data class DealingResponse(
    val list: List<Dealing>
)
