package me.algosketch.itunes.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.algosketch.itunes.core.exceptions.NetworkException
import me.algosketch.itunes.core.exceptions.UnknownException
import me.algosketch.itunes.data.model.TrackQuery
import me.algosketch.itunes.data.model.TrackResponse
import me.algosketch.itunes.data.remote.api.TrackApi

class TrackPagingSource(
    private val api: TrackApi,
    private val keyword: String,
) : PagingSource<Int, TrackResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val query = TrackQuery(
                keyword = keyword,
                page = nextPageNumber,
                limit = params.loadSize,
            )

            val response = api.getTracks(
                term = query.keyword,
                offset = query.offset,
                limit = query.limit,
                entity = query.entity,
            )

            val data = if (response.isSuccessful) {
                response.body()?.results ?: throw UnknownException
            } else {
                throw NetworkException
            }

            LoadResult.Page(
                data = data,
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