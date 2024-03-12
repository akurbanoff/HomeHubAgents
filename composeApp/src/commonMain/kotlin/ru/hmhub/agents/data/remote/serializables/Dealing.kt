package ru.hmhub.agents.data.remote.serializables

import kotlinx.serialization.Serializable

@Serializable
data class Dealing(
    val amount: Int,
    val comment: String?,
    val created_at: String,
    val email: String?,
    val id: Int,
    val manager: Manager,
    val name: String?,
    val note: String?,
    val phones: List<String>,
    val source: Source?,
    val stage: Stage,
    val sub_stage: SubStage,
    val updated_at: String
)

@Serializable
data class Stage(
    val id: Int,
    val name: String
)

@Serializable
data class SubStage(
    val id: Int,
    val name: String
)
