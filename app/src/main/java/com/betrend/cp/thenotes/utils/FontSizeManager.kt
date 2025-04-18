package com.betrend.cp.thenotes.utils

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

// Responsável por gerencia o estado da fonte adaptada no NotesTakerScreen
class FontSizeManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("FontPrefs", Context.MODE_PRIVATE)
    var fontSize by mutableStateOf(loadFontSize())
        private set
    var selsectedFontSize by mutableStateOf(fontSize)
        private set
    var showBottomSheet by mutableStateOf(false)
        private set
    private fun loadFontSize(): TextUnit {
        val savedSize = sharedPreferences.getFloat("font_size", 14f)
        return savedSize.sp
    }
    fun saveFontSize(size: TextUnit) {
        selsectedFontSize = size
        fontSize = size
        sharedPreferences.edit().putFloat("font_size", size.value).apply()
    }
    fun toggleBottomSheet() {
        showBottomSheet = !showBottomSheet
    }
}