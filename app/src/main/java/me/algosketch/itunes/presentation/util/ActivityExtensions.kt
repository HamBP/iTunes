package me.algosketch.itunes.presentation.util

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes

fun Activity.showToast(@StringRes messageId: Int) {
    val message = resources.getString(messageId)
    showToast(message)
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}