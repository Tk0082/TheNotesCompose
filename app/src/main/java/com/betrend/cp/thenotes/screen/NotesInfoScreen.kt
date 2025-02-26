package com.betrend.cp.thenotes.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesInfoScreen(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column (
                Modifier
                    .fillMaxSize()
                    .background(YellowNoteLL),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "InfoScreen",
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    color = Color(0xFF505050)
                )
            }
        }
    )
}

@Preview(name = "Info")
@Composable
fun InfoPreview(){
    NotesInfoScreen()
}