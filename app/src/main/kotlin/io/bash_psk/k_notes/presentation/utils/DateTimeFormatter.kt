package io.bash_psk.k_notes.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Long.shortDateFormat(): String {

    val pattern = "dd/MM/yyyy - hh:mm:ss a"

    return SimpleDateFormat(
        pattern
    ).format(this)
}

@SuppressLint("SimpleDateFormat")
fun Long.longDateFormat(): String {

    val pattern = "EEEE, MMM dd, yyyy - hh:mm:ss a - z"

    return SimpleDateFormat(
        pattern
    ).format(this)
}