package me.algosketch.itunes.presentation.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import me.algosketch.itunes.databinding.LoadStateItemBinding

class LoadStateViewHolder(
    private val binding: LoadStateItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.pbProgress.isVisible = loadState is LoadState.Loading
    }

    companion object {
        fun from(parent: ViewGroup): LoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadStateItemBinding.inflate(inflater, parent, false)

            return LoadStateViewHolder(binding)
        }
    }
}