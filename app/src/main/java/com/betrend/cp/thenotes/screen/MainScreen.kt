package com.betrend.cp.thenotes.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.NoteActivity
import com.betrend.cp.thenotes.R.*
import com.betrend.cp.thenotes.entities.NotesList
import com.betrend.cp.thenotes.entities.PinnedNotesList
import com.betrend.cp.thenotes.model.NoteViewModel
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNote3

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: NoteViewModel = viewModel()){
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    val pinnedNote by viewModel.pinnedNotes.collectAsState(initial = emptyList())
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Intent(context, NoteActivity::class.java).also {
                        context.startActivity(it)
                    }
                },
                content = {
                    Icon(
                            Icons.Default.Add,
                            contentDescription = "Criar Nota",
                            tint = YellowNote3
                        )
                }
            )
        }
    ) {
        Column (
            Modifier.fillMaxSize(),
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

}

@Preview(name = "Main")
@Composable
fun MainPreview(){
    MainScreen()
}