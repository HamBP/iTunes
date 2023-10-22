package me.algosketch.itunes.presentation.util

import androidx.annotation.StringRes
import me.algosketch.itunes.R
import me.algosketch.itunes.core.exceptions.ItException
import me.algosketch.itunes.core.exceptions.NetworkException
import me.algosketch.itunes.core.exceptions.UnknownException

val ItException.messageId get(): @StringRes Int {
    return when(this) {
        is NetworkException -> R.string.network_exception
        is UnknownException -> R.string.unknown_exception
    }
}