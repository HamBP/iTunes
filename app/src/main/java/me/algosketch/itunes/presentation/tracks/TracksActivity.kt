package me.algosketch.itunes.presentation.tracks

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.algosketch.itunes.R
import me.algosketch.itunes.core.exceptions.ItException
import me.algosketch.itunes.databinding.ActivityTracksBinding
import me.algosketch.itunes.presentation.util.messageId

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
            handleError(loadStates)
        }

        lifecycleScope.launch {
            viewModel.trackFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun handleError(loadStates: CombinedLoadStates) {
        val exception = when {
            loadStates.refresh is LoadState.Error -> (loadStates.refresh as LoadState.Error).error
            loadStates.append is LoadState.Error -> (loadStates.append as LoadState.Error).error
            else -> null
        }

        exception?.let { e ->
            if (e is ItException) showToast(e.messageId)
            else showToast(e.message ?: "알 수 없는 에러")
        }
    }

    private fun showToast(@StringRes messageId: Int) {
        val message = resources.getString(messageId)
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}