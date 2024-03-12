package ru.hmhub.agents.data.remote.serializables

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeSerializable(
    val created_at: String,
    val department: Department?,
    val email: String,
    val first_name: String?,
    val id: Int,
    val is_active: Boolean,
    val last_name: String,
    val last_visit_at: String,
    val local_phone: String?,
    val middle_name: String?,
    val office: Office?,
    val phone: String?,
    val photos: List<String>?,
    val position: String,
    val role: Role,
    val second_phone: String?,
    val show_on_site: Boolean,
    val supervisor: Supervisor?,
    val updated_at: String
)

@Serializable
data class Office(
    val id: Int,
    val name: String
)

@Serializable
data class Role(
    val id: Int,
    val name: String
)

@Serializable
data class Supervisor(
    val id: Int,
    val name: String
)
