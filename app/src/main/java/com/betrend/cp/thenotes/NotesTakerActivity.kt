package com.betrend.cp.thenotes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.betrend.cp.thenotes.ui.screen.NotesTakerScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme

@RequiresApi(Build.VERSION_CODES.O)
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
