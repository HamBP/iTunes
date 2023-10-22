package me.algosketch.itunes.data

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
            response.body()?.results ?: throw IllegalStateException("알 수 없는 에러입니다.")
        } else {
            throw RuntimeException("네트워크 통신 과정에서 에러가 발생했어요.")
        }
    }
}