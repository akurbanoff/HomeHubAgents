package ru.hmhub.agents.data.remote.responseSerializables

import kotlinx.serialization.Serializable
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable

@Serializable
data class EmployeeResponse(
    val list: List<EmployeeSerializable>
)
