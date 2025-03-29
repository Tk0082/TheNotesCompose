package com.betrend.cp.thenotes.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesInfoScreen(){
    TheNotesTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(YellowNoteLL)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(YellowNoteLL),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(id = R.string.app_name), textAlign = TextAlign.Center,
                    color = Graffit,
                    style = TextStyle(
                        fontSize = 45.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            GraffitL,
                            offset = Offset.VisibilityThreshold,
                            blurRadius = 15f
                        )
                    ),
                    modifier = Modifier
                        .padding(vertical = 50.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Info")
@Composable
fun InfoPreview(){
    NotesInfoScreen()
}