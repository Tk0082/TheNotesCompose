package com.betrend.cp.thenotes.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GraffitLL
import com.betrend.cp.thenotes.ui.theme.GraffitLLL
import com.betrend.cp.thenotes.ui.theme.GreenNote
import com.betrend.cp.thenotes.ui.theme.GreenNoteL
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNote4
import com.betrend.cp.thenotes.ui.theme.YellowNote5
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL
import com.betrend.cp.thenotes.ui.theme.brushNoteBld
import com.betrend.cp.thenotes.ui.theme.brushNoteBll
import com.betrend.cp.thenotes.ui.theme.brushNoteGrd
import com.betrend.cp.thenotes.ui.theme.brushNoteGrl
import com.betrend.cp.thenotes.ui.theme.brushNoteLid
import com.betrend.cp.thenotes.ui.theme.brushNoteLil
import com.betrend.cp.thenotes.ui.theme.brushNoteOrd
import com.betrend.cp.thenotes.ui.theme.brushNoteOrl
import com.betrend.cp.thenotes.ui.theme.brushNoteRdd
import com.betrend.cp.thenotes.ui.theme.brushNoteRdl
import com.betrend.cp.thenotes.ui.theme.brushNoteWnd
import com.betrend.cp.thenotes.ui.theme.brushNoteWnl
import com.betrend.cp.thenotes.ui.theme.brushNoteYld
import com.betrend.cp.thenotes.ui.theme.brushNoteYll


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

@Composable
fun brushYellow(): Brush{
    val backNote = listOf(brushNoteYll, brushNoteYld)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushGreen(): Brush{
    val backNote = listOf(brushNoteGrl, brushNoteGrd)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushRed(): Brush{
    val backNote = listOf(brushNoteRdl, brushNoteRdd)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushBlue(): Brush{
    val backNote = listOf(brushNoteBll, brushNoteBld)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushOrange(): Brush{
    val backNote = listOf(brushNoteOrl, brushNoteOrd)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushLime(): Brush{
    val backNote = listOf(brushNoteLil, brushNoteLid)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushWine(): Brush{
    val backNote = listOf(brushNoteWnl, brushNoteWnd)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}
@Composable
fun brushSelect(): Brush{
    val backNote = listOf(GraffitLLL, GraffitLL)
    val brushborderbutton = Brush.verticalGradient(backNote)
    return brushborderbutton
}