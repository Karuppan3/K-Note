package io.bash_psk.k_notes.presentation.utils

import android.content.Context
import android.widget.Toast

fun Context.setToast(
    message: String
) {

    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}