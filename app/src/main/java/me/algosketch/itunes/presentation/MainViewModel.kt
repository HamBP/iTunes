package me.algosketch.itunes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.algosketch.itunes.data.remote.TrackPagingSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trackPagingSource: TrackPagingSource,
) : ViewModel() {

    val trackFlow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        trackPagingSource
    }.flow
        .cachedIn(viewModelScope)

}