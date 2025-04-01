package com.betrend.cp.thenotes.entities

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitD
import com.betrend.cp.thenotes.ui.theme.GreenNoteD
import com.betrend.cp.thenotes.ui.theme.GreenNoteDD
import com.betrend.cp.thenotes.ui.theme.NoteItemError
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.utils.brushNotePin
import com.betrend.cp.thenotes.utils.textScrool

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItemPin(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onUnpinClick: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean
) {
    Box(
        Modifier
            .width(130.dp)
            .height(100.dp)
            .padding(2.dp)
    ) {

        val interactionSource = remember { MutableInteractionSource() }
        val rippleIndication  = ripple(true, 5.dp, Graffit)

        val extraPadding by animateDpAsState(
            if (expanded) 25.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ), label = ""
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .shadow(2.dp, RoundedCornerShape(15.dp))
                .border(0.5.dp, GreenNoteD, RoundedCornerShape(15.dp))
                .background(brush = brushNotePin(), RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    val scrollState = rememberScrollState()
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                    if (note.isPinned) {
                        Image(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.mipmap.thenotes),
                            contentDescription = null,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    // Item a visualizar com Item Expandido
                    if (!expanded) {
                        Column(

                            modifier = Modifier
                                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                        ) {
                            Text(
                                text = note.content,
                                style = TextStyle(
                                    color = Graffit,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, true)
                                    .padding(start = 5.dp),
                                maxLines = 2
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
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                            ) {
                                Icon(
                                    Icons.Default.Share,
                                    tint = YellowNoteDD,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(2.dp)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rippleIndication ,
                                            onClick = onShareClick),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Icon(
                                    Icons.Default.Delete,
                                    tint = NoteItemError,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(vertical = 1.dp)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rippleIndication ,
                                            onClick = onDeleteClick),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.size(10.dp))
                                Icon(
                                    Icons.Default.Clear,
                                    tint = GreenNoteDD,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rippleIndication ,
                                            onClick = onUnpinClick),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemPinPreview(){
    NoteItemPin(note = Note(id = 0, name = "Teste", content = "Content", time = "1:00", isPinned = false), onClick = {}, onLongClick = {}, onShareClick = {}, onUnpinClick = {}, onDeleteClick = {}, expanded = true)
}