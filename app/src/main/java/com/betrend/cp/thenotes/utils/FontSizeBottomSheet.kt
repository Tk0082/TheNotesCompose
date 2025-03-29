package com.betrend.cp.thenotes.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.YellowNote4
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL

@Composable
fun FontSizeBottomSheet(fontSizeManager: FontSizeManager, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(YellowNote4, RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
    ) {
        Column (modifier = Modifier.padding(15.dp)){
            Text(
                text = "Selecione o tamanho da fonte:",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = GraffitL
                ),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            val fontSizes = listOf(8.sp, 10.sp, 12.sp, 14.sp, 16.sp, 18.sp, 20.sp, 22.sp)
            fontSizes.forEach { size ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            fontSizeManager.selsectedFontSize = size
                            fontSizeManager.fontSize = size
                            fontSizeManager.showBottomSheet = false
                            onDismiss()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = fontSizeManager.selsectedFontSize == size,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = YellowNoteD,
                            unselectedColor = YellowNoteLL,
                            disabledColor = YellowNoteLL
                        ),
                        onClick = {
                            fontSizeManager.selsectedFontSize = size
                            fontSizeManager.fontSize = size
                            fontSizeManager.showBottomSheet = false
                            onDismiss()
                        }
                    )
                    Text(
                        text = "${size.value.toInt()} sp",
                        fontSize = size,
                        color = GraffitL
                    )
                }
            }
        }
    }
}

@Preview(name = "NoteAct")
@Composable
fun BottomPreview() = FontSizeBottomSheet(FontSizeManager(), onDismiss = {})