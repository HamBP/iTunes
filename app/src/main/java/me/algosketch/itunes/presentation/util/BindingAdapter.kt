package me.algosketch.itunes.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImageUrl(view: ImageView, url: String){
    Glide.with(view.context)
        .load(url)
        .into(view)
}