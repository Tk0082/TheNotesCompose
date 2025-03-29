package com.betrend.cp.thenotes.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getDate(): String? {
    val date = LocalDateTime.now()
    val format_date = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")
    return date.format(format_date)
}