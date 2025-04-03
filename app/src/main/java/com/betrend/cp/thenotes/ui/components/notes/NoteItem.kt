package com.betrend.cp.thenotes.ui.components.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.data.local.entities.Note
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLLL
import com.betrend.cp.thenotes.utils.textScrool

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .padding(2.dp)
                .shadow(2.dp, RoundedCornerShape(15.dp))
                .background(YellowNoteLLL, RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .weight(1f, true)
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                ) {
                    LaunchedEffect(Unit) {
                        textScrool(scrollState)
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f, true)
                            .horizontalScroll(scrollState, enabled = true)
                            .padding(start = 5.dp),
                        text = note.name,
                        style = TextStyle(
                            color = GraffitD,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(
                        text = note.content,
                        style = TextStyle(
                            color = Graffit,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp),
                        maxLines = 10
                    )
                    Text(
                        text = note.time,
                        style = TextStyle(
                            color = Graffit,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 5.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemPreview(){
    NoteItem(note = Note(id = 0, name = "Teste", content = "Content", time = "1:00", isPinned = false), onClick = {}, onLongClick = {})
}