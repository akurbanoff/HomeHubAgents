package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable
import ru.hmhub.agents.data.remote.serializables.Client

@Serializable
data class ClientResponse(
    val list: List<Client>
)
