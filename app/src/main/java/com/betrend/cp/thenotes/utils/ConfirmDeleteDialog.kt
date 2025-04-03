package com.betrend.cp.thenotes.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.data.local.entities.Note
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteDDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL

@Composable
fun ConfirmDeleteDialog(
    note: Note,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        title = { Text(text = "Confirmar Remoção") },
        text = { Text("Deseja remover permanentemente a nota \n${note.name}") },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = YellowNoteDD,
                    contentColor = YellowNoteLLL
                )
            ) {
                Text(text = "Remover", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancelar", color = YellowNoteDD, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = YellowNoteLL,
        modifier = Modifier.background(brush = brushMenu(), shape = RoundedCornerShape(28.dp)),
        titleContentColor = YellowNoteDDD,
        textContentColor = GraffitL,
    )
}
