package ru.hmhub.agents.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.hmhub.agents.data.remote.responseSerializables.DealingResponse
import ru.hmhub.agents.data.remote.responseSerializables.EmployeeResponse
import ru.hmhub.agents.data.remote.responseSerializables.PasswordResponse
import ru.hmhub.agents.data.remote.serializables.Dealing
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable
import ru.hmhub.agents.data.remote.serializables.InsertPassword
import ru.hmhub.agents.utils.HashUtils

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

    override suspend fun getDealings(skip: Int): List<Dealing> {
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
        val response = client.request(urlString = url + "/insert_password?id=$id&password=$doubleHashedPassword") {
            method = HttpMethod.Post
        }.bodyAsText()

        val result = Json.decodeFromString<PasswordResponse>(response)
        if(result.status in 400..<600){
            throw Exception("Проблема на стороне сервера")
        } else {
            return HttpStatusCode(value = result.status, "")
        }
    }

    override suspend fun checkPassword(id: Int, password: String): HttpStatusCode {
        val response = client.request(urlString = "$url/check_password?id=$id&password=$password") {
            method = HttpMethod.Get
        }.bodyAsText()

        val result = Json.decodeFromString<PasswordResponse>(response)
        if(result.status in 400..<500){
            throw IllegalArgumentException("Такого пользователя не существует либо введенный пароль неверен")
        } else if(result.status in 500..<600) {
            throw Exception("Проблема на стороне сервера")
        } else {
            return HttpStatusCode(value = result.status, "")
        }
    }
}
