package com.betrend.cp.thenotes.entities

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotesList(
    notes: List<Note>,
    onPinnedClick: (Note) -> Unit,
    onDeletClick: (Note) -> Unit,
    onSharedClick: (Note) -> Unit,
    onSaveClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(notes){ note ->
            NoteItem(note, onSaveClick, onDeletClick, onSharedClick, onPinnedClick, modifier)
        }
    }
}

@Composable
fun PinnedNotesList(
    notes: List<Note>,
    onPinnedClick: (Note) -> Unit,
    onDeletClick: (Note) -> Unit,
    onSharedClick: (Note) -> Unit,
    onSaveClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){
    LazyRow {
        items(notes){ note ->
            if(note.isPinned){
                NoteItem(note, onSaveClick, onDeletClick, onSharedClick, onPinnedClick, modifier)
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onSaveClick: (Note) -> Unit,
    onDeletClick: (Note) -> Unit,
    onSharedClick: (Note) -> Unit,
    onPinnedClick: (Note) -> Unit,
    modifier: Modifier = Modifier
){}