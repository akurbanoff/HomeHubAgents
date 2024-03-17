package ru.hmhub.agents.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.hmhub.agents.data.remote.requestSerializables.NewsRequest
import ru.hmhub.agents.data.remote.responseSerializables.DealingResponse
import ru.hmhub.agents.data.remote.responseSerializables.EmployeeResponse
import ru.hmhub.agents.data.remote.responseSerializables.NewsInsertResponse
import ru.hmhub.agents.data.remote.responseSerializables.NewsResponse
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
import ru.hmhub.agents.data.remote.responseSerializables.PasswordExistResponse
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
        val doubleHashedPassword = doubleHash(password)
        val response = client.request(urlString = "$url/insert_password?id=$id&password=$doubleHashedPassword") {
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
        val doubleHashedPassword = doubleHash(password)
        val response = client.request(urlString = "$url/check_password?id=$id&password=$doubleHashedPassword") {
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

    override suspend fun checkPasswordExist(id: Int): Boolean {
        val response = client.request(urlString = "$url/check_password_exist?id=$id"){
            method = HttpMethod.Get
        }.bodyAsText()

        val result = Json.decodeFromString<PasswordExistResponse>(response)
        return result.isExist
    }

    override suspend fun getNews(skip: Int): List<NewsSerializable> {
        val response = client.get(urlString = "$url/get_news?skip=$skip&limit=5").bodyAsText()

        val result = Json.decodeFromString<NewsResponse>(response)

        return result.list
    }

    override suspend fun insertNews(news: NewsRequest): Int {
        val response = client.post(urlString = "$url/insert_news"){
            this.setBody(news)
        }.bodyAsText()

        val result = Json.decodeFromString<NewsInsertResponse>(response)

        return result.status
    }

    private fun doubleHash(password: String): String{
        val hash = HashUtils()
        val hashedPassword = hash.sha256(password)
        val doubleHashedPassword = hash.sha256(hashedPassword)
        return doubleHashedPassword
    }
}
