package ru.hmhub.agents.data.remote

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.hmhub.agents.data.remote.requestSerializables.NewsRequest
import ru.hmhub.agents.data.remote.responseSerializables.NewsSerializable
import ru.hmhub.agents.ui.states.UiState

interface RemoteRepository {
    suspend fun getEmployees(): Flow<UiState>
    //suspend fun getDealings(skip: Int): Flow<UiState>
    suspend fun insertPassword(id: Int, password: String): Flow<UiState>
    suspend fun checkPassword(id: Int, password: String): Flow<UiState>

    suspend fun checkPasswordExist(id: Int): Flow<UiState>

    fun getNews(): Flow<PagingData<NewsSerializable>>
    suspend fun insertNews(news: NewsRequest): Flow<UiState>
}