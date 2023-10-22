package me.algosketch.itunes.presentation.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import me.algosketch.itunes.data.repository.TrackRepository
import me.algosketch.itunes.data.remote.TrackPagingSource
import me.algosketch.itunes.presentation.model.toModel
import javax.inject.Inject

@HiltViewModel
class TracksViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
) : ViewModel() {

    private val _keyword = MutableStateFlow("greenday")
    val keyword: StateFlow<String> = _keyword.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val trackFlow = keyword.flatMapLatest {
        Pager(PagingConfig(pageSize = 10)) {
            TrackPagingSource(trackRepository, it)
        }.flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { it.toModel() }
            }
    }

}