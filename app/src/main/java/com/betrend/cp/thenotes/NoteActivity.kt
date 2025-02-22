package com.betrend.cp.thenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.betrend.cp.thenotes.screen.NoteScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme

class NoteActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheNotesTheme {
                NoteScreen()
            }
        }
    }
}
