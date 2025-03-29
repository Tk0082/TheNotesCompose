package com.betrend.cp.thenotes.utils

import android.content.Context
import android.content.Intent

fun shareText(context: Context, name: String, content: String) {
    val link = "TheNotes\nhttps://play.google.com/store/apps/details?id=com.gbferking.thenotes\n\n"
    val title = "Compartilhar: $name"
    val note = "$link $name\n\n $content "
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_TEXT, note)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, null))
}