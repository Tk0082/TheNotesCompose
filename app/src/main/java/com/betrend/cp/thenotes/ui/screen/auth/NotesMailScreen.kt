package com.betrend.cp.thenotes.ui.screen.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.betrend.cp.thenotes.NotesInfoActivity
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesMailScreen(){
    val context = LocalContext.current
    TheNotesTheme {
        Scaffold (
            modifier = Modifier
                .fillMaxSize()
                .background(YellowNoteLL)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(YellowNoteLL)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        Intent(context, NotesInfoActivity::class.java).also {
                            context.startActivity(it)
                        }
                    },
                    content = {
                        Image(painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null
                        )
                    },
                )
            }
        }
    }
}

@Preview(name = "MailScreen")
@Composable
fun MailPreview(){
    NotesMailScreen()
}