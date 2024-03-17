package ru.hmhub.agents.data.remote.api

import io.ktor.http.HttpStatusCode
import ru.hmhub.agents.data.remote.requestSerializables.NewsRequest
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
import ru.hmhub.agents.data.remote.serializables.Dealing
import ru.hmhub.agents.data.remote.serializables.EmployeeSerializable

interface Api {
    suspend fun getEmployees(): List<EmployeeSerializable>
    suspend fun getDealings(skip: Int): List<Dealing>
    suspend fun insertPassword(id: Int, password: String): HttpStatusCode
    suspend fun checkPassword(id: Int, password: String): HttpStatusCode
    suspend fun checkPasswordExist(id: Int): Boolean
    suspend fun getNews(skip: Int): List<NewsSerializable>
    suspend fun insertNews(news: NewsRequest): Int
}