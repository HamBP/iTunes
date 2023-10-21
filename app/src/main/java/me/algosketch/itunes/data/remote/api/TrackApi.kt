package me.algosketch.itunes.data.remote.api

import me.algosketch.itunes.data.model.PageResponse
import me.algosketch.itunes.data.model.TrackResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {

    @GET("search")
    suspend fun getTracks(
        @Query("term") term: String = "greenday",
        @Query("entity") entity: String = "song",
    ): Response<PageResponse<TrackResponse>>
}