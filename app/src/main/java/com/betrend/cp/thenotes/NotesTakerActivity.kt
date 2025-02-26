package com.betrend.cp.thenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.betrend.cp.thenotes.screen.NotesTakerScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme

class NotesTakerActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheNotesTheme {
                NotesTakerScreen()
            }
        }
    }
}
