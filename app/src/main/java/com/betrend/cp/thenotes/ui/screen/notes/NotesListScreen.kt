package com.betrend.cp.thenotes.ui.screen.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.betrend.cp.thenotes.NotesTakerActivity
import com.betrend.cp.thenotes.R.mipmap
import com.betrend.cp.thenotes.R.string
import com.betrend.cp.thenotes.data.local.NotesDatabase
import com.betrend.cp.thenotes.data.local.repository.NotesRepository
import com.betrend.cp.thenotes.ui.components.notes.NoteItem
import com.betrend.cp.thenotes.ui.components.notes.NoteItemPin
import com.betrend.cp.thenotes.ui.theme.Graffit
import com.betrend.cp.thenotes.ui.theme.NoteError
import com.betrend.cp.thenotes.ui.theme.NoteItemError
import com.betrend.cp.thenotes.ui.theme.Transparent
import com.betrend.cp.thenotes.ui.theme.YellowNoteDD
import com.betrend.cp.thenotes.ui.theme.YellowNoteL
import com.betrend.cp.thenotes.ui.viewmodel.NotesListViewModel
import com.betrend.cp.thenotes.ui.viewmodel.factory.NotesListViewModelFactory
import com.betrend.cp.thenotes.utils.ConfirmDeleteDialog
import com.betrend.cp.thenotes.utils.NoteSelectDialog
import com.betrend.cp.thenotes.utils.brushBackNote
import com.betrend.cp.thenotes.utils.brushBlue
import com.betrend.cp.thenotes.utils.brushBorderButton
import com.betrend.cp.thenotes.utils.brushBorderNote
import com.betrend.cp.thenotes.utils.brushGreen
import com.betrend.cp.thenotes.utils.brushLime
import com.betrend.cp.thenotes.utils.brushOrange
import com.betrend.cp.thenotes.utils.brushRed
import com.betrend.cp.thenotes.utils.brushWine
import com.betrend.cp.thenotes.utils.brushYellow
import com.betrend.cp.thenotes.utils.shareText
import com.betrend.cp.thenotes.utils.showMessage
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesListScreen() {
    val context = LocalContext.current

    // Criar o repositório
    val notesRepository = NotesRepository(NotesDatabase.getNotes(context).notesDao())

    // Criar o ViewModel
    val viewModel: NotesListViewModel = viewModel(factory = NotesListViewModelFactory(notesRepository) )

    // Coletando o estado de UI
    val uiState by viewModel.uiState.collectAsState()

    // Observe as notas normais e pinadas
    val notes by viewModel.notes.collectAsState()
    val notesPin by viewModel.pinnedNotes.collectAsState()

    val npin = notesPin.size

    // Estado do Dialog de remoção de notas
    val showDialogId = remember { mutableStateOf<String?>(null) }

    // Configurando a expansão do item
    val focusedNoteId = remember { mutableStateOf<String?>(null) }

    // Pesquisa
    val txSearch = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var isSearchActive by remember { mutableStateOf(false) }

    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        focusManager.clearFocus() // Remove o foco da SearchBar
    }

    LaunchedEffect(txSearch.value) {
        if (txSearch.value.isEmpty()) {
            viewModel.fetchNotes()
            viewModel.fetchPinNotes()
        } else {
            viewModel.searchNotes(txSearch.value)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .border(1.dp, brushBorderButton(), RoundedCornerShape(20.dp)),
                containerColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 3.dp,
                    pressedElevation = 10.dp
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    Intent(context, NotesTakerActivity::class.java).also {
                        it.putExtra("noteId", -1)
                        context.startActivity(it)
                    }
                },
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Criar Nota",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        content = {
            Column (
                Modifier
                    .fillMaxSize()
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = mipmap.thenotes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(34.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Text(
                        text = stringResource(id = string.app_name),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(MaterialTheme.colorScheme.onSurfaceVariant, offset = Offset.VisibilityThreshold, blurRadius = 5f)
                        ),
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f, true)
                            .shadow(
                                3.dp, RoundedCornerShape(50.dp),
                                true, MaterialTheme.colorScheme.onSurfaceVariant
                            )
                    ) {
                        SearchBarDefaults.InputField(
                            query = txSearch.value,
                            onQueryChange = { newQuery ->
                                txSearch.value = newQuery
                                viewModel.searchNotes(newQuery)
                            },
                            onSearch = {
                                focusManager.clearFocus(true)
                            },
                            expanded = false,
                            onExpandedChange = { },
                            placeholder = {
                                Text(
                                    "Buscar Notas...",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = {
                                if (txSearch.value > 0.toString()) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            txSearch.value = ""
                                            focusManager.clearFocus(true)
                                        }
                                    )
                                }
                            },
                            colors = SearchBarDefaults.inputFieldColors(
                                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                                focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                                cursorColor = MaterialTheme.colorScheme.tertiary,
                                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onTertiary,
                                focusedTrailingIconColor = MaterialTheme.colorScheme.onTertiary,
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
                                disabledTextColor = MaterialTheme.colorScheme.onTertiary,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = brushBackNote(),
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .height(50.dp)
                                .border(.5.dp, brushBorderNote(), RoundedCornerShape(50.dp))
                                .onFocusChanged { focusState ->
                                    if (focusState.isFocused) {
                                        focusState.hasFocus
                                    } else {
                                        // Oculta o teclado quando perder o foco
                                        focusManager.clearFocus(true)
                                    }
                                }
                        )
                    }
                }
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else if (uiState.error != null) {
                    Text(text = uiState.error.toString(), color = NoteError, modifier = Modifier.padding(16.dp))
                } else {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Lista de Notas pinadas
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                            flingBehavior = ScrollableDefaults.flingBehavior()
                        ) {
                            items(notesPin) { note ->
                                val isExpanded = focusedNoteId.value == note.id.toString()

                                // Fechar botões depois de 3s
                                LaunchedEffect(isExpanded) {
                                    if (isExpanded) {
                                        delay(3000)
                                        focusedNoteId.value = null
                                    }
                                }
                                NoteItemPin(
                                    note = note,
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        // Evento de clicar na nota para editar
                                        Intent(context, NotesTakerActivity::class.java).also {
                                            it.putExtra("noteId", note.id)
                                            context.startActivity(it)
                                        }
                                    },
                                    onLongClick = {
                                        // Expande ou contrai a nota clicada
                                        focusedNoteId.value =
                                            if (focusedNoteId.value == note.id.toString()) null else note.id.toString()
                                    },
                                    onDeleteClick = {
                                        // Chamar o Dialog de Remoção
                                        showDialogId.value = note.id.toString()
                                        focusedNoteId.value = null
                                    },
                                    onUnpinClick = {
                                        viewModel.pinNote(note)
                                        focusedNoteId.value = null
                                    },
                                    onShareClick = {
                                        shareText(context, note.name, note.content)
                                        focusedNoteId.value = null
                                    },
                                    expanded = isExpanded
                                )
                                // Chama o Dialog
                                if (showDialogId.value == note.id.toString()) {
                                    ConfirmDeleteDialog(
                                        note = note,
                                        onDismiss = { showDialogId.value = null },
                                        onConfirm = {
                                            viewModel.removeNote(note)
                                            showDialogId.value = null
                                        }
                                    )
                                }
                            }
                        }
                        if (notesPin.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .alpha(.8f)
                                    .padding(2.dp)
                                    .align(Alignment.BottomStart)
                                    .shadow(3.dp, CircleShape, true, Graffit)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Center)
                                        .background(NoteError, CircleShape)
                                        .border(1.dp, NoteItemError, CircleShape)
                                ) {
                                    Text(
                                        text = npin.toString(),
                                        modifier = Modifier.align(Alignment.Center),
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            color = Graffit,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                    // Lista de Notas gerais
                    LazyColumn(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 5.dp)
                            .weight(1f, true)
                    ) {
                        items(notes, key = { it.id }) { note ->
                            var showColorDialog by remember { mutableStateOf(false) }

                            // Converter a cor da nota para Brush
                            val currentBrush = when (note.color) {
                                    "amarelo" -> brushYellow()
                                    "verde" -> brushGreen()
                                    "vermelho" -> brushRed()
                                    "azul" -> brushBlue()
                                    "laranja" -> brushOrange()
                                    "limao" -> brushLime()
                                    "vinho" -> brushWine()
                                    else -> brushYellow()
                                }


                            val dismissState = rememberDismissState(
                                // Ações de Deletar e Compartilhar no Swipe
                                confirmStateChange = {
                                    when (it) {
                                        DismissValue.DismissedToStart -> {
                                            showDialogId.value = note.id.toString()
                                            false
                                        }
                                        DismissValue.DismissedToEnd -> {
                                            shareText(context, note.name, note.content)
                                            false // Mantém a nota na lista após compartilhar
                                        }
                                        else -> false
                                    }
                                }
                            )
                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                                background = {
                                    val color by animateColorAsState(
                                        when(dismissState.targetValue){
                                            DismissValue.DismissedToStart ->NoteError
                                            DismissValue.DismissedToEnd -> YellowNoteL
                                            else -> Transparent
                                        },
                                        label = ""
                                    )
                                    val icon = when (dismissState.dismissDirection) {
                                        DismissDirection.EndToStart -> Icons.Default.Delete
                                        DismissDirection.StartToEnd -> Icons.Default.Share
                                        else -> null
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color, RoundedCornerShape(15.dp))
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = if (dismissState.dismissDirection == DismissDirection.EndToStart) Alignment.CenterEnd else Alignment.CenterStart
                                    ) {
                                        icon?.let {
                                            Icon(
                                                it,
                                                contentDescription = null,
                                                tint = if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                                    NoteItemError else YellowNoteDD
                                            )
                                        }
                                    }
                                },
                                dismissContent = {
                                    NoteItem(
                                        note = note,
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            Intent(context, NotesTakerActivity::class.java).also {
                                                it.putExtra("noteId", note.id)
                                                context.startActivity(it)
                                            }
                                        },
                                        onLongClick = {
                                            showColorDialog = true

                                        },
                                        brush = currentBrush
                                    )
                                    if (showDialogId.value == note.id.toString()) {
                                        ConfirmDeleteDialog(
                                            note = note,
                                            onDismiss = { showDialogId.value = null },
                                            onConfirm = {
                                                viewModel.removeNote(note)
                                                showMessage(context, "Nota, ${note.name} deletada!")
                                                showDialogId.value = null
                                            }
                                        )
                                    }
                                }
                            )
                            NoteSelectDialog(
                                showDialog = showColorDialog,
                                onDismiss = { showColorDialog = false },
                                onColorSelected = { colorHex ->
                                    viewModel.updateNote(note.copy(color = colorHex))
                                },
                                onBrushSelected = { },
                                onConfirm = {
                                    viewModel.fetchNotes()
                                    showColorDialog = false
                                },
                                onFavorite = { viewModel.pinNote(note) },
                                initialColor = note.color,
                                initialBrush = currentBrush
                            )
                        }
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "Notes")
@Composable
fun NotesPreview() {
    NotesListScreen()
}
