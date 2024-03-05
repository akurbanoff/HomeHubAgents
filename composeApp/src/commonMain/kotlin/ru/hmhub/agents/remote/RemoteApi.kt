package ru.hmhub.agents.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.hmhub.agents.remote.serializables.Client
import ru.hmhub.agents.remote.serializables.Dealing
import ru.hmhub.agents.remote.serializables.EmployeeSerializable

class RemoteApi {
    private val client = HttpClient(CIO){install(HttpCache)}
    private val url = "http://127.0.0.1"

    suspend fun getEmployees(): Flow<List<EmployeeSerializable>> {
        val response: Flow<List<EmployeeSerializable>> = client.request(urlString = url + "/get_employees"){
            method = HttpMethod.Get
        }.body()
        return response
    }

    suspend fun getDealings(): Flow<List<Dealing>> {
        val response: Flow<List<Dealing>> = client.request(urlString = url + "/get_dealings"){
            method = HttpMethod.Get
        }.body()
        return response
    }

    suspend fun getClients(): Flow<List<Client>> {
        val response: Flow<List<Client>> = client.request(urlString = url + "/get_clients"){
            method = HttpMethod.Get
        }.body()
        return response
    }
}
