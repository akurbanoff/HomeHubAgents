package ru.hmhub.agents.data.pagingSources

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.hmhub.agents.data.remote.api.Api
import ru.hmhub.agents.data.remote.serializables.Dealing

class PagingRepositoryImpl(
    private val api: Api
) : PagingRepository {

    override fun getDealings(skip: Int): Flow<PagingData<Dealing>> {
        TODO("Not yet implemented")
    }

}