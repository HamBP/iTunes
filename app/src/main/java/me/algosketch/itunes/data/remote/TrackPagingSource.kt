package me.algosketch.itunes.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.algosketch.itunes.data.model.TrackResponse
import me.algosketch.itunes.data.remote.api.TrackApi
import javax.inject.Inject

class TrackPagingSource @Inject constructor(
    private val api: TrackApi,
) : PagingSource<Int, TrackResponse>() {

    private val query = "greenday"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val offset = nextPageNumber * params.loadSize
            val response = api.getTracks(
                term = query,
                offset = offset,
                limit = params.loadSize,
            )

            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TrackResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}