package me.algosketch.itunes.data.model

data class TrackResponse(
    val trackId: Long,
    val trackName: String,
    val artworkUrl60: String,
    val collectionName: String,
    val artistName: String,
)
