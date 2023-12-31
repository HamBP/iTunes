package me.algosketch.itunes.presentation.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.algosketch.itunes.databinding.TrackItemBinding
import me.algosketch.itunes.presentation.model.TrackModel

class TrackViewHolder private constructor(
    private val binding: TrackItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TrackModel?) {
        binding.item = item
    }

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TrackItemBinding.inflate(inflater, parent, false)

            return TrackViewHolder(binding)
        }
    }
}