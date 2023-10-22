package me.algosketch.itunes.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.algosketch.itunes.data.repository.TrackRepository
import me.algosketch.itunes.data.model.TrackQuery
import me.algosketch.itunes.data.model.TrackResponse

class TrackPagingSource(
    private val repository: TrackRepository,
    private val keyword: String,
) : PagingSource<Int, TrackResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackResponse> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repository.getTracks(
                TrackQuery(
                    keyword = keyword,
                    page = nextPageNumber,
                    limit = params.loadSize,
                )
            )

            LoadResult.Page(
                data = response,
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