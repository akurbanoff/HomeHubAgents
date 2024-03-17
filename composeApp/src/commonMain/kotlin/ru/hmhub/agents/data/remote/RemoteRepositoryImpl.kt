package ru.hmhub.agents.data.remote

import androidx.paging.PagingSourceFactory
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.hmhub.agents.data.pagingSources.NewsPagingSource
import ru.hmhub.agents.data.remote.api.Api
import ru.hmhub.agents.data.remote.requestSerializables.NewsRequest
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
            println(e.stackTraceToString())
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

    override suspend fun checkPasswordExist(id: Int): Flow<UiState> {
        return try {
            flowOf(UiState.Success(api.checkPasswordExist(id)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }

//    override suspend fun getNews(skip: Int): Flow<UiState> {
//        return try {
//            flowOf(UiState.Success(api.getNews(skip)))
//        } catch (e: Exception){
//            flowOf(UiState.Error(e))
//        }
//    }

    override fun getNews() = Pager(
        config = PagingConfig(
            pageSize = 1
        ),
        pagingSourceFactory = {NewsPagingSource(api = api)}
    ).flow

    override suspend fun insertNews(news: NewsRequest): Flow<UiState> {
        return try {
            flowOf(UiState.Success(api.insertNews(news)))
        } catch (e: Exception){
            flowOf(UiState.Error(e))
        }
    }
}