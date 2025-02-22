package com.betrend.cp.thenotes.model

import androidx.lifecycle.ViewModel
import com.betrend.cp.thenotes.entities.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoteViewModel: ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() =_notes

    private val _pinnedNotes = MutableStateFlow<List<Note>>(emptyList())
    val pinnedNotes: StateFlow<List<Note>> get() = _pinnedNotes

    // Favoritar Nota
    fun onPinnedClick(note: Note){

    }

    // Deletar Nota
    fun onDeletClick(note: Note){

    }

    // Compartilhar Nota
    fun onSharedClick(note: Note){

    }

    // Salvar Nota
    fun onSaveClick(note: Note){

    }
}
