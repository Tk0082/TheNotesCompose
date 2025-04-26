package com.betrend.cp.thenotes.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betrend.cp.thenotes.data.local.repository.NotesRepository
import com.betrend.cp.thenotes.data.local.entities.Note
import com.betrend.cp.thenotes.ui.states.NotesListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class NotesListViewModel(private val repository: NotesRepository): ViewModel() {

    private var _uiState: MutableStateFlow<NotesListUiState> = MutableStateFlow(NotesListUiState())
    val uiState = _uiState.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _pinnedNotes = MutableStateFlow<List<Note>>(emptyList())
    val pinnedNotes = _pinnedNotes.asStateFlow()

    init {
        fetchNotes()
        fetchPinNotes()
    }

    // Atualizar estado das notas
    fun fetchNotes() {
        viewModelScope.launch {
            repository.getAllNotes().collect { notesList ->
                _uiState.value = NotesListUiState(notes = notesList)
                _notes.value = notesList.filter { !it.isPinned }
            }
        }
    }

    // Atualizar estado das notas pinadas
    fun fetchPinNotes() {
        viewModelScope.launch {
            repository.pinned().collect { pinList ->
                _uiState.value = NotesListUiState(notes = pinList)
                _pinnedNotes.value = pinList.filter { it.isPinned }
            }
        }
    }

    fun searchNotes(query: String) {
        viewModelScope.launch {
            repository.searchNotes(query).collect { notesList ->
                _notes.value = notesList.filter { !it.isPinned }
                _pinnedNotes.value = notesList.filter { it.isPinned }
            }
        }
    }

    fun pinNote(note: Note){
        viewModelScope.launch {
            repository.pin(note)
        }
    }

    fun removeNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }
}
