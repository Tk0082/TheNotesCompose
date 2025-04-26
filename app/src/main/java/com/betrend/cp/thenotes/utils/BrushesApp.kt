package com.betrend.cp.thenotes.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.betrend.cp.thenotes.ui.theme.ButtonD
import com.betrend.cp.thenotes.ui.theme.ButtonL
import com.betrend.cp.thenotes.ui.theme.GreenNote
import com.betrend.cp.thenotes.ui.theme.GreenNoteL
import com.betrend.cp.thenotes.ui.theme.White
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNote4
import com.betrend.cp.thenotes.ui.theme.YellowNote5
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL


@Composable
fun brushBackButton(): Brush{
    val backBtn = listOf(YellowNoteLL, YellowNote)
    val brushbackbutton = Brush.verticalGradient(backBtn)
    return brushbackbutton
}

@Composable
fun brushBorderButton(): Brush{
    val backNote = listOf(YellowNoteLLL, YellowNoteDD)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}

@Composable
fun brushBackNote(): Brush {
    val backNote = listOf(YellowNote5, YellowNote4)
    val brushbacknote = Brush.verticalGradient(backNote)
    return brushbacknote
}

@Composable
fun brushBorderNote(): Brush{
    val backNote = listOf(YellowNoteLL, YellowNoteD)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}

@Composable
fun brushNotePin(): Brush {
    val backNote = listOf(GreenNoteL, GreenNote)
    val brushbacknote = Brush.verticalGradient(backNote)
    return brushbacknote
}

@Composable
fun brushMenu(): Brush {
    val backNote = listOf(YellowNoteLLL, YellowNoteLL)
    val brushbacknote = Brush.verticalGradient(backNote)
    return brushbacknote
}

@Composable
fun brushBorderMenu(): Brush{
    val backNote = listOf(YellowNoteL, YellowNote)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}

@Composable
fun brushPanel(): Brush{
    val backNote = listOf(YellowNoteLLL, YellowNote)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
