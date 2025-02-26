@file:Suppress("UNUSED_VARIABLE")

package com.betrend.cp.thenotes.screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomSheetScaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBottomSheetScaffoldState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberBottomSheetState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.R
import com.betrend.cp.thenotes.model.NoteViewModel
import com.betrend.cp.thenotes.ui.theme.BackTxtD
import com.betrend.cp.thenotes.ui.theme.ButtonD
import com.betrend.cp.thenotes.ui.theme.ButtonL
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.GraffitL
import com.betrend.cp.thenotes.ui.theme.GraffitLL
import com.betrend.cp.thenotes.ui.theme.TheNotesTheme
import com.betrend.cp.thenotes.ui.theme.White
import com.betrend.cp.thenotes.ui.theme.YellowNote
import com.betrend.cp.thenotes.ui.theme.YellowNote4
import com.betrend.cp.thenotes.ui.theme.YellowNoteD
import com.betrend.cp.thenotes.ui.theme.YellowNoteLL
import com.betrend.cp.thenotes.utils.FontSizeBottomSheet
import com.betrend.cp.thenotes.utils.FontSizeManager
import kotlinx.coroutines.launch
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NotesTakerScreen(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    val pinnedNote by viewModel.pinnedNotes.collectAsState(initial = emptyList())

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val backTxshape = listOf(White, BackTxtD)
    val brushtx = Brush.verticalGradient(backTxshape)

    val backNote = listOf(White, YellowNote4)
    val brushnote = Brush.verticalGradient(backNote)

    val backBtn = listOf(ButtonL, ButtonD)
    val backbtn = Brush.verticalGradient(backBtn)

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var note by remember { mutableStateOf(TextFieldValue("")) }

    val fontSizeManager = remember { FontSizeManager() }
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
                    .padding(horizontal = 6.dp, vertical = 8.dp)
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
                            .background(brushnote, RoundedCornerShape(8.dp))
                    ) {
                        BasicTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier
                                .height(50.dp)
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
                                    if (name.text.isEmpty()) {
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
                            value = note,
                            onValueChange = { note = it },
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
                                    if (note.text.isEmpty()) {
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
                    ) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    bottomSheetState.expand()
                                }
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .shadow(1.dp, RoundedCornerShape(60.dp))
                                .background(YellowNote, RoundedCornerShape(50.dp))
                                .border(.5.dp, brushnote, RoundedCornerShape(50.dp)),
                            content = {
                                Row(
                                    modifier = Modifier.padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Image(
                                        painter = painterResource(id = R.mipmap.thenotes),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .padding(top = 4.dp, bottom = 6.dp)
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .width(150.dp)
                                .shadow(1.dp, RoundedCornerShape(60.dp))
                                .background(YellowNote, RoundedCornerShape(50.dp))
                                .border(.5.dp, brushnote, RoundedCornerShape(50.dp)),
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
                                            .padding(top = 4.dp, bottom = 6.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "NoteAct")
@Composable
fun NotePreview(){
    NotesTakerScreen()
}


// Captalizar texto de nota
fun capitalizarTexto(text: String): String {
    if (text.isEmpty()) return text

    val words = text.split(". ")
    val capitalizeWords = words.map { sentence ->
        sentence.split(" ").mapIndexed { index, word ->
            if (index == 0 || sentence.equals(index-1)) word.capitalize(Locale.getDefault()) else word
        }.joinToString(" ")
    }
    return capitalizeWords.joinToString(". ")
}