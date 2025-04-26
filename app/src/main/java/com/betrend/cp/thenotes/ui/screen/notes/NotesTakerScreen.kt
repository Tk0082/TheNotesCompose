package com.betrend.cp.thenotes.ui.screen.notes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.MainActivity
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.data.local.NotesDatabase
import com.betrend.cp.thenotes.data.local.repository.NotesRepository
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GraffitLL
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.ui.viewmodel.NotesTakerViewModel
import com.betrend.cp.thenotes.ui.viewmodel.factory.NotesTakerViewModelFactory
import com.betrend.cp.thenotes.utils.FontSizeBottomSheet
import com.betrend.cp.thenotes.utils.FontSizeManager
import com.betrend.cp.thenotes.utils.brushBackNote
import com.betrend.cp.thenotes.utils.brushBorderButton
import com.betrend.cp.thenotes.utils.getDate
import com.betrend.cp.thenotes.utils.showMessage
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NotesTakerScreen() {
    val context = LocalContext.current
    val notesRepository = NotesRepository(NotesDatabase.getNotes(context).notesDao())
    val viewModel: NotesTakerViewModel = viewModel(factory = NotesTakerViewModelFactory(notesRepository))

    // Recuperando o ID da nota passado via Intent
    val noteId = (context as? Activity)?.intent?.getIntExtra("noteId", -1) ?: -1
    if (noteId != -1) {
        viewModel.loadNote(noteId) // Carrega a nota para edição
    }

    val uiState by viewModel.uiState.collectAsState() // Usando collectAsState para observar o estado

    val scope = rememberCoroutineScope()
    rememberScaffoldState()

    val fontSizeManager = remember { FontSizeManager(context) }
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    TheNotesTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                FontSizeBottomSheet(fontSizeManager = fontSizeManager) {
                    scope.launch {
                        bottomSheetState.collapse()
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            modifier = Modifier
                .fillMaxSize()
                .background(YellowNoteLL)
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(YellowNoteLL)
                    .padding(padding)
                    .padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 3.dp)
            ) {
                Column(
                    modifier = Modifier.background(YellowNoteLL),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f, true)
                            .shadow(3.dp, RoundedCornerShape(8.dp))
                            .background(brushBackNote(), RoundedCornerShape(8.dp))
                    ) {
                        BasicTextField(
                            value = uiState.name,
                            onValueChange = viewModel::onNameChange,
                            modifier = Modifier
                                .height(40.dp)
                                .padding(5.dp)
                                .fillMaxWidth(),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = GraffitL,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            cursorBrush = SolidColor(YellowNote),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (uiState.name.isEmpty()) {
                                        Text(
                                            text = "Título",
                                            color = GraffitLL,
                                            style = TextStyle(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            )
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(YellowNoteD)
                        )
                        BasicTextField(
                            value = uiState.content,
                            onValueChange = viewModel::onContentChange,
                            modifier = Modifier
                                .padding(horizontal = 5.dp, vertical = 10.dp)
                                .fillMaxSize(),
                            textStyle = TextStyle(
                                color = GraffitL,
                                fontSize = fontSizeManager.fontSize // Usando o tamanho da fonte configurado
                            ),
                            cursorBrush = SolidColor(YellowNote),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (uiState.content.isEmpty()) {
                                        Text(
                                            text = "Digite sua nota..",
                                            color = GraffitLL,
                                            style = TextStyle(
                                                fontWeight = FontWeight.Normal,
                                                fontSize = fontSizeManager.fontSize // Usando o tamanho da fonte configurado
                                            ),
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Default
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    bottomSheetState.expand()
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .shadow(1.dp, RoundedCornerShape(60.dp))
                                .background(YellowNote, RoundedCornerShape(50.dp))
                                .border(.5.dp, brushBorderButton(), RoundedCornerShape(50.dp)),
                            content = {
                                Row(
                                    modifier = Modifier.padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.font_ajust),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .padding(top = 5.dp)
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        IconButton(
                            onClick = {
                                val date = getDate()
                                showMessage(context, "Title: ${uiState.name}\ncontent: ${uiState.content} - $date")
                                viewModel.saveNote()
                                Intent(context, MainActivity::class.java).also {
                                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    context.startActivity(it)
                                }
                            },
                            modifier = Modifier
                                .height(40.dp)
                                .width(160.dp)
                                .shadow(1.dp, RoundedCornerShape(60.dp))
                                .background(YellowNote, RoundedCornerShape(50.dp))
                                .border(.5.dp, brushBorderButton(), RoundedCornerShape(50.dp)),
                            content = {
                                Row(
                                    modifier = Modifier.padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "SALVAR",
                                        color = Graffit,
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Image(
                                        painter = painterResource(id = R.mipmap.thenotes),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .padding(bottom = 4.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
            // Efeito de fechar o BottomSheet ao voltar, ou finalizar Activity se estiver fechado
            BackHandler {
                if (bottomSheetState.isExpanded) {
                    scope.launch {
                        bottomSheetState.collapse()
                    }
                } else {
                    (context as? Activity)?.finish()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "NoteAct")
@Composable
fun NotePreview(){
    NotesTakerScreen()
}


// Captalizar texto de nota
//fun capitalizarTexto(text: String): String {
//    if (text.isEmpty()) return text
//
//    val words = text.split(". ")
//    val capitalizeWords = words.map { sentence ->
//        sentence.split(" ").mapIndexed { index, word ->
//            if (index == 0 || sentence.equals(index-1)) word.capitalize(Locale.getDefault())  else word
//        }.joinToString(" ")
//    }
//    return capitalizeWords.joinToString(". ")
//}