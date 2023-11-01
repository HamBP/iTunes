package me.algosketch.itunes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.algosketch.itunes.data.model.TrackResponse
import me.algosketch.itunes.data.remote.TrackPagingSource
import me.algosketch.itunes.data.remote.api.TrackApi
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val api: TrackApi,
) {

    fun getTracksFlow(keyword: String): Flow<PagingData<TrackResponse>> {
        return Pager(PagingConfig(pageSize = 10)) {
            TrackPagingSource(api, keyword)
        }.flow
    }
}