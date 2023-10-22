package me.algosketch.itunes.data.model

data class TrackQuery(
    val keyword: String = "",
    val entity: String = "song",
    val page: Int = 1,
    val limit: Int = 10,
) {
    val offset: Int = (page - 1) * limit
}