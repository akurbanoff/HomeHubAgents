package ru.hmhub.agents.remote

import io.ktor.http.HttpStatusCode
import ru.hmhub.agents.remote.serializables.Dealing
import ru.hmhub.agents.remote.serializables.EmployeeSerializable

interface Api {
    suspend fun getEmployees(): List<EmployeeSerializable>
    suspend fun getDealings(): List<Dealing>
    suspend fun insertPassword(id: Int, password: String): HttpStatusCode
    suspend fun checkPassword(id: Int, password: String): HttpStatusCode
}