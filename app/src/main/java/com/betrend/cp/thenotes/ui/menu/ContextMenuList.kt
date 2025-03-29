package com.betrend.cp.thenotes.ui.menu

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.entities.Note
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.utils.brushBorderMenu
import com.betrend.cp.thenotes.utils.brushMenu

@Composable
fun ContextMenuList(
    note: Note,
    onPin: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(brush = brushMenu())
                .border(0.5.dp, brushBorderMenu(), RoundedCornerShape(12.dp))
                .padding(10.dp)
        ) {
            ContextMenuItem("Fixar / Remover", Icons.Default.Star, onPin)
            ContextMenuItem("Compartilhar", Icons.Default.Share, onShare)
            ContextMenuItem("Excluir", Icons.Default.Delete, onDelete)
        }
    }
}


@Composable
fun ContextMenuItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = YellowNoteD)
        Text(text, color = Graffit, modifier = Modifier.padding(start = 10.dp))
    }
}

@Preview
@Composable
fun MenuPreview(){
    ContextMenuList(note = Note(id = 0, name = "Teste", content = "Content", time = "1:00", isPinned = false), onPin = {}, onShare = {}, onDelete = {}, modifier = Modifier)
}