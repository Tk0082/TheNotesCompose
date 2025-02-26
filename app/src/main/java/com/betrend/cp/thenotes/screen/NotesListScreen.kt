package com.betrend.cp.thenotes.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.NotesTakerActivity
import com.betrend.cp.thenotes.entities.NotesList
import com.betrend.cp.thenotes.entities.PinnedNotesList
import com.betrend.cp.thenotes.model.NoteViewModel
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesListScreen(viewModel: NoteViewModel = viewModel()){

    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    val pinnedNote by viewModel.pinnedNotes.collectAsState(initial = emptyList())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowNoteLL),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Intent(context, NotesTakerActivity::class.java).also {
                        context.startActivity(it)
                    }
                },
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Criar Nota",
                        tint = YellowNoteDD
                    )
                }
            )
        },
        content = {
            Column (
                Modifier.fillMaxSize().background(YellowNoteLL),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "MainScreen",
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    color = Color(0xFF505050)
                )
                // Lista de Notas Pinadas
                PinnedNotesList(
                    pinnedNote,
                    viewModel::onPinnedClick,
                    viewModel::onSaveClick,
                    viewModel::onDeletClick,
                    viewModel::onSharedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFbbffbb))
                        .height(height = 80.dp)
                )

                // Lista de Notas Totais
                NotesList(
                    notes,
                    viewModel::onPinnedClick,
                    viewModel::onSaveClick,
                    viewModel::onDeletClick,
                    viewModel::onSharedClick
                )
            }
        }
    )
}

@Preview(name = "Notes")
@Composable
fun NotesPreview(){
    NotesListScreen()
}