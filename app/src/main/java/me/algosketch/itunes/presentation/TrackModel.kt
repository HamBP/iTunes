package me.algosketch.itunes.presentation

import me.algosketch.itunes.data.model.TrackResponse

data class TrackModel(
    val trackName: String,
    val artworkUrl60: String,
    val collectionName: String,
    val artistName: String,
)

fun TrackResponse.toModel(): TrackModel {
    return TrackModel(
        trackName = trackName,
        artworkUrl60 = artworkUrl60,
        collectionName = collectionName,
        artistName = artistName,
    )
}