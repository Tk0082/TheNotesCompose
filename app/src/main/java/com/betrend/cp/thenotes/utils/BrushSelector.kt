package com.betrend.cp.thenotes.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD

@Composable
fun BrushSelector(
    onColorSelected: (String) -> Unit,
    onBrushSelected: (Brush) -> Unit
) {
    // Lista de brushes disponíveis
    val brushes = listOf(
        "amarelo" to brushYellow(),
        "verde" to brushGreen(),
        "vermelho" to brushRed(),
        "azul" to brushBlue(),
        "laranja" to brushOrange(),
        "limao" to brushLime(),
        "vinho" to brushWine(),
    )
    // Estado para o Brush selecionado
    var selectedBrush by remember { mutableStateOf(brushes[0].second) }
    var selectedColor by remember { mutableStateOf(brushes[0].first) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Exibe um botão para cada Brush
        brushes.forEach { (label, brush) ->
            Box(
                modifier = Modifier
                    .shadow(2.dp, CircleShape, true, Graffit)
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(brush)
                        .border(
                            .1.dp,
                            if (brush == selectedBrush) YellowNoteDD else Color.Transparent,
                            CircleShape
                        )
                        .clickable {
                            selectedBrush = brush
                            selectedColor = label
                            onBrushSelected(selectedBrush) // Retorno do Brush selecionado
                            onColorSelected(selectedColor)
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun BrushSelectorPreview(){
    BrushSelector(onBrushSelected = {}, onColorSelected = {})
}
