package ru.hmhub.agents.data.remote.serializables

import kotlinx.serialization.Serializable

@Serializable
data class Client (
    val amount: Int,
    val comment: String?,
    val created_at: String,
    val department: Department?,
    val direction: Direction,
    val email: String?,
    val id: Int,
    val is_picked: Boolean,
    val manager: Manager?,
    val name: String?,
    val phone: String,
    val picked_at: String,
    val quality: Quality,
    val source: Source?,
    val type: Type,
    val updated_at: String
)

@Serializable
data class Department(
    val id: Int,
    val name: String
)

@Serializable
data class Direction(
    val id: Int,
    val name: String
)

@Serializable
data class Manager(
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val phone: String
)

@Serializable
data class Quality(
    val id: Int,
    val name: String
)

@Serializable
data class Source(
    val id: Int,
    val name: String
)

@Serializable
data class Type(
    val id: Int,
    val name: String
)
