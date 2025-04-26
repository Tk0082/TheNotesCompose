package com.betrend.cp.thenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.betrend.cp.thenotes.ui.screen.MainScreen
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheNotesTheme {
                MainScreen()
            }
        }
    }
}
