package me.algosketch.itunes.presentation.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import me.algosketch.itunes.data.remote.TrackPagingSource
import me.algosketch.itunes.presentation.model.toModel
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val trackPagingSource: TrackPagingSource,
) : ViewModel() {

    val trackFlow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        trackPagingSource
    }.flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { it.toModel() }
        }

}