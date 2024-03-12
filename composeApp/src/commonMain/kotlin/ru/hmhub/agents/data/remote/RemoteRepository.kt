package ru.hmhub.agents.data.remote

import kotlinx.coroutines.flow.Flow
import ru.hmhub.agents.ui.states.UiState

interface RemoteRepository {
    suspend fun getEmployees(): Flow<UiState>
    //suspend fun getDealings(skip: Int): Flow<UiState>
    suspend fun insertPassword(id: Int, password: String): Flow<UiState>
    suspend fun checkPassword(id: Int, password: String): Flow<UiState>
}