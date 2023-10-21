package me.algosketch.itunes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import me.algosketch.itunes.data.remote.TrackPagingSource
import me.algosketch.itunes.data.repository.TrackRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    private val trackPagingSource: TrackPagingSource,
) : ViewModel() {

    private val _tracks = MutableStateFlow(emptyList<TrackModel>())
    val tracks: StateFlow<List<TrackModel>> = _tracks.asStateFlow()

    val trackFlow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        trackPagingSource
    }.flow
        .cachedIn(viewModelScope)

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        trackRepository.getTracks()
            .onStart {
                // todo loading true
            }
            .onEach { responses ->
                _tracks.value = responses.map { it.toModel() }
            }
            .onCompletion {
                // todo loading false
            }
            .catch {
                // todo exception handling
            }
            .launchIn(viewModelScope)
    }
}