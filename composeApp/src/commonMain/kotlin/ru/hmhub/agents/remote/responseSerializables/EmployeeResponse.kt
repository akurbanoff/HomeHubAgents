package ru.hmhub.agents.remote.responseSerializables

import kotlinx.serialization.Serializable
import ru.hmhub.agents.remote.serializables.EmployeeSerializable

@Serializable
data class EmployeeResponse(
    val list: List<EmployeeSerializable>
)
