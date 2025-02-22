package com.betrend.cp.thenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.model.NoteViewModel
import com.betrend.cp.thenotes.screen.MainScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheNotesTheme {
                val viewModel: NoteViewModel = viewModel()
                MainScreen(viewModel)
            }
        }
    }
}
