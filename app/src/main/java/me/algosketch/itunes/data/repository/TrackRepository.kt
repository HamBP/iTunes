package me.algosketch.itunes.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.algosketch.itunes.data.model.TrackResponse
import me.algosketch.itunes.data.remote.api.TrackApi
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val api: TrackApi,
) {

    fun getTracks(): Flow<List<TrackResponse>> = flow {
        val response = api.getTracks()

        if (response.isSuccessful) {
            val tracks = response.body()?.results ?: throw RuntimeException("알 수 없는 에러입니다.")
            emit(tracks)
        } else {
            throw RuntimeException("네트워크 통신 과정에서 에러가 발생했어요.")
        }
    }
}