package ru.hmhub.agents.data.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.hmhub.agents.data.remote.api.Api
import ru.hmhub.agents.data.remote.serializables.Dealing

class DealingPagingSource(
    private val api: Api
) : PagingSource<Int, Dealing>(){
    private final val ITEM_AMOUNT_PER_SKIP = 5
    override fun getRefreshKey(state: PagingState<Int, Dealing>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ITEM_AMOUNT_PER_SKIP)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ITEM_AMOUNT_PER_SKIP)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dealing> {
        return try {
            val skip = params.key ?: 0
            val response = api.getDealings(skip = skip)

            LoadResult.Page(
                data = response,
                prevKey = if(skip == 0) null else skip.minus(ITEM_AMOUNT_PER_SKIP),
                nextKey = if(response.isEmpty()) null else skip.plus(ITEM_AMOUNT_PER_SKIP)
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}