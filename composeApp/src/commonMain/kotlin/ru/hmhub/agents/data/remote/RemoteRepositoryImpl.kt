package ru.hmhub.agents.data.remote

import androidx.paging.PagingSourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.hmhub.agents.data.remote.api.Api
import ru.hmhub.agents.data.remote.serializables.Dealing
import ru.hmhub.agents.ui.states.UiState

class RemoteRepositoryImpl(
    private val api: Api
) : RemoteRepository {
    override suspend fun getEmployees() : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.getEmployees()))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
//    override suspend fun getDealings(skip: Int) : Flow<UiState>{
//        return try {
//            flowOf(UiState.Success(api.getDealings(skip)))
//        } catch (e: Exception){
//            flowOf(UiState.Error(e))
//        }
//    }
    override suspend fun insertPassword(id: Int, password: String) : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.insertPassword(id, password)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
    override suspend fun checkPassword(id: Int, password: String) : Flow<UiState>{
        return try {
            flowOf(UiState.Success(api.checkPassword(id, password)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
}