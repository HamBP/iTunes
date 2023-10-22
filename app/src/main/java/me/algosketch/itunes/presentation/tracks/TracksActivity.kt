package me.algosketch.itunes.presentation.tracks

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.algosketch.itunes.R
import me.algosketch.itunes.databinding.ActivityTracksBinding
import me.algosketch.itunes.presentation.model.TrackModel
import me.algosketch.itunes.presentation.model.toModel

@AndroidEntryPoint
class TracksActivity : AppCompatActivity() {
    private val viewModel: TracksViewModel by viewModels()
    private lateinit var binding: ActivityTracksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tracks)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupTracks()
    }

    private fun setupTracks() {
        val adapter = TrackAdapter(TrackComparator).apply {
            binding.rvTracks.adapter = withLoadStateFooter(ItLoadStateAdapter())
        }

        adapter.addLoadStateListener { loadStates ->
            val errorMessage = when {
                loadStates.refresh is LoadState.Error -> (loadStates.refresh as LoadState.Error).error.message
                loadStates.append is LoadState.Error -> (loadStates.append as LoadState.Error).error.message
                else -> null
            }

            errorMessage?.let { message ->
                showToast(message)
            }
        }

        lifecycleScope.launch {
            viewModel.trackFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}