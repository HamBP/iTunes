package me.algosketch.itunes.data.repository

import me.algosketch.itunes.core.exceptions.NetworkException
import me.algosketch.itunes.core.exceptions.UnknownException
import me.algosketch.itunes.data.model.TrackQuery
import me.algosketch.itunes.data.model.TrackResponse
import me.algosketch.itunes.data.remote.api.TrackApi
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val api: TrackApi,
) {

    suspend fun getTracks(query: TrackQuery): List<TrackResponse> {
        val response = api.getTracks(
            term = query.keyword,
            entity = query.entity,
            offset = query.offset,
            limit = query.limit,
        )

        return if (response.isSuccessful) {
            response.body()?.results ?: throw UnknownException
        } else {
            throw NetworkException
        }
    }
}