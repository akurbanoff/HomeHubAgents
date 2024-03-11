package ru.hmhub.agents.remote.responseSerializables

import kotlinx.serialization.Serializable
import ru.hmhub.agents.remote.serializables.Client

@Serializable
data class ClientResponse(
    val list: List<Client>
)
