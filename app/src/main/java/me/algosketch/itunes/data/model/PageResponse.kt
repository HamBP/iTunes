package me.algosketch.itunes.data.model

data class PageResponse<T>(
    val resultCount: Int,
    val results: List<T>,
)