package com.betrend.cp.thenotes.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp

class FontSizeManager {
    var fontSize by mutableStateOf(18.sp)
    var selsectedFontSize by mutableStateOf(fontSize)
    var showBottomSheet by mutableStateOf(false)
}