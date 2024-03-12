package ru.hmhub.agents.data.pagingSources

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.hmhub.agents.data.remote.serializables.Dealing

interface PagingRepository {
    fun getDealings(skip: Int): Flow<PagingData<Dealing>>
}