package ru.hmhub.agents.ui.states

import ru.hmhub.agents.remote.serializables.Client
import ru.hmhub.agents.remote.serializables.Dealing
import ru.hmhub.agents.remote.serializables.EmployeeSerializable

data class RemoteState(
    val employees: List<EmployeeSerializable> = emptyList(),
    val dealings: List<Dealing> = emptyList(),
    val clients: List<Client> = emptyList()
)