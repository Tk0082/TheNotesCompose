package com.betrend.cp.thenotes.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import kotlinx.coroutines.delay

suspend fun textScrool(scrollState: ScrollState){
    while (true) {
        scrollState.animateScrollTo(scrollState.maxValue, animationSpec = tween(durationMillis = 3000, easing = LinearEasing))
        delay(1000) // Pausa antes de voltar
        scrollState.animateScrollTo(0, animationSpec = tween(durationMillis = 3000, easing = LinearEasing))
        delay(500) // Pausa antes de reiniciar
    }
}