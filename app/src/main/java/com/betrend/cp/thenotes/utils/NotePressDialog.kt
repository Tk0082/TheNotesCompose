package com.betrend.cp.thenotes.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GreenNote
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL

@Composable
fun NoteSelectDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onFavorite: () -> Unit,
    onColorSelected: (String) -> Unit,
    onBrushSelected: (Brush) -> Unit,
    onConfirm: () -> Unit,
    initialColor: String,
    initialBrush: Brush
) {
    if (showDialog) {
        var selectedColor by remember { mutableStateOf(initialColor) }
        var selectedBrush by remember { mutableStateOf(initialBrush) }

        AlertDialog(
            title = { Text(text = "Cor da Nota") },
            text = {
                Column {
                    // Pré-visualização da cor selecionada
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(.1.dp, YellowNoteDD, RoundedCornerShape(50.dp))
                            .shadow(2.dp, RoundedCornerShape(50.dp), true, Graffit)
                            .background(
                                if (selectedColor == initialColor) initialBrush else selectedBrush
                            )
                            .padding(bottom = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BrushSelector(
                        onBrushSelected = { brush ->
                            selectedBrush = brush
                        },
                        onColorSelected = { color ->
                            selectedColor = color
                        }
                    )
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = {
                        onColorSelected(selectedColor)
                        onConfirm()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowNoteDD,
                        contentColor = YellowNoteLLL
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 5.dp
                    )
                ) {
                    Text(text = "Aplicar Cor", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onFavorite()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenNote,
                        contentColor = YellowNoteLLL
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 5.dp
                    )
                ) {
                    Image(painter = painterResource(id = R.drawable.pin), contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Fixar", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = YellowNoteLL,
            modifier = Modifier.background(brush = brushMenu(), shape = RoundedCornerShape(28.dp)),
            titleContentColor = YellowNoteDDD,
            textContentColor = GraffitL
        )
    }
}

@Composable
fun NoteSaveDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onBrushSelected: (Brush) -> Unit,
    onColorSelected: (String) -> Unit,
    initialColor: String = "amarelo",
    initialBrush: Brush = brushYellow(),
) {
    if (showDialog) {
        var selectedBrush by remember { mutableStateOf(initialBrush) }
        var selectedColor by remember { mutableStateOf(initialColor) }

        AlertDialog(
            title = { Text(text = "Cor da Nota") },
            text = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(
                                .1.dp,
                                YellowNoteDD,
                                RoundedCornerShape(50.dp)
                            )
                            .shadow(2.dp, RoundedCornerShape(50.dp), true, Graffit)
                            .background(
                                if (selectedColor == initialColor) initialBrush else selectedBrush,
                                RoundedCornerShape(50.dp)
                            )
                            .padding(bottom = 16.dp)
                            .clickable {
                                onColorSelected(selectedColor)
                            }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    BrushSelector(
                        onBrushSelected = { brush ->
                            selectedBrush = brush
                        },
                        onColorSelected = { color ->
                            selectedColor = color
                        }
                    )
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = {
                        onColorSelected(selectedColor)
                        onConfirm()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowNoteDD,
                        contentColor = YellowNoteLLL
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 3.dp,
                        pressedElevation = 5.dp
                    )
                ) {
                    Text(text = "Aplicar Cor", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = YellowNoteLL,
            modifier = Modifier.background(brush = brushMenu(), shape = RoundedCornerShape(28.dp)),
            titleContentColor = YellowNoteDDD,
            textContentColor = GraffitL
        )
    }
}

@Preview(name = "PressDialogPreview")
@Composable
fun NotePressDialogPreview(){
    var showDialog by remember { mutableStateOf(true) }
    val selectedBrush = YellowNoteLL

    NoteSelectDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onColorSelected = {},
        onConfirm = {
            println("Cor confirmada: #${selectedBrush}")
        },
        onBrushSelected = {},
        initialColor = "amarelo",
        initialBrush = brushYellow(),
        onFavorite = {}
    )
}
@Preview(name = "SaveDialogPreview")
@Composable
fun NoteSaveDialogPreview() {
    NoteSaveDialog(
        showDialog = true,
        onDismiss = {},
        initialBrush = brushYellow(),
        onBrushSelected = {},
        onConfirm = {},
        onColorSelected = {}
    )
}