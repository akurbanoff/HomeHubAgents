package ru.hmhub.agents.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.Request
import io.ktor.http.isSuccess
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import ru.hmhub.agents.remote.responseSerializables.ClientResponse
import ru.hmhub.agents.remote.responseSerializables.DealingResponse
import ru.hmhub.agents.remote.responseSerializables.EmployeeResponse
import ru.hmhub.agents.remote.serializables.Client
import ru.hmhub.agents.remote.serializables.Dealing
import ru.hmhub.agents.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.remote.serializables.InsertPassword
import ru.hmhub.agents.utils.HashUtils
import ru.hmhub.agents.ui.states.UiState

class RemoteApi: Api {
    private val client = HttpClient(CIO){install(HttpCache)}
    private val url = "http://45.130.42.144:8080"

    override suspend fun getEmployees(): List<EmployeeSerializable> {
        val response = client.request(urlString = url + "/get_employees") {
                method = HttpMethod.Get
            }.bodyAsText()
        val employees = Json.decodeFromString<EmployeeResponse>(response)
        return employees.list
    }

    override suspend fun getDealings(): List<Dealing> {
        val response = client.request(urlString = url + "/get_dealings") {
            method = HttpMethod.Get
        }.bodyAsText()
        val dealings = Json.decodeFromString<DealingResponse>(response)
        return dealings.list
    }

//    suspend fun getClients(): Flow<List<Client>> {
//        val response: Flow<List<Client>> = flow {
//            val json = client.request(urlString = url + "/get_clients"){
//                method = HttpMethod.Get
//            }
//            val clients = Json.decodeFromString<ClientResponse>(json.toString())
//            emit(clients.list)
//        }
//        return response
//    }

    override suspend fun insertPassword(id: Int, password: String) : HttpStatusCode{
        val hash = HashUtils()
        val hashedPassword = hash.sha256(password)
        val doubleHashedPassword = hash.sha256(hashedPassword)
        val response = client.request(urlString = url + "/insert_password") {
            method = HttpMethod.Post
            this.setBody(InsertPassword(id = id, password = doubleHashedPassword))
        }
        return response.status
    }

    override suspend fun checkPassword(id: Int, password: String): HttpStatusCode {
        val response = client.request(urlString = "$url/check_password?id=$id&password=$password") {
            method = HttpMethod.Get
        }
        return response.status
    }
}
