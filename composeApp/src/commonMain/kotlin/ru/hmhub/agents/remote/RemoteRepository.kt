package ru.hmhub.agents.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.hmhub.agents.ui.states.UiState

class RemoteRepository(
    private val api: RemoteApi
) {
    suspend fun getEmployees() : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.getEmployees()))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
    suspend fun getDealings() : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.getDealings()))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
    suspend fun insertPassword(id: Int, password: String) : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.insertPassword(id, password)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
    suspend fun checkPassword(id: Int, password: String) : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.checkPassword(id, password)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
}