package com.betrend.cp.thenotes.utils

import android.content.Context
import android.content.Intent

fun shareText(context: Context, title: String, content: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "${title}\n\n $content")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar Usando.."))
}