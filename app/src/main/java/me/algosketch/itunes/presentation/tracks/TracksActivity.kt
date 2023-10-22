package me.algosketch.itunes.presentation.tracks

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.algosketch.itunes.R
import me.algosketch.itunes.databinding.ActivityTracksBinding
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

        lifecycleScope.launch {
            viewModel.trackFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}