package me.algosketch.itunes.presentation

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
import me.algosketch.itunes.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupTracks()
    }

    private fun setupTracks() {
        val adapter = TrackAdapter(TrackComparator)
        binding.rvTracks.adapter = adapter
        lifecycleScope.launch {
            viewModel.trackFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData.map { it.toModel() })
            }
        }
    }
}