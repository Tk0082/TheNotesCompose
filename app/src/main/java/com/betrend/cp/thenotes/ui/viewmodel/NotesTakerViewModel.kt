package com.betrend.cp.thenotes.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betrend.cp.thenotes.data.local.repository.NotesRepository
import com.betrend.cp.thenotes.data.local.entities.Note
import com.betrend.cp.thenotes.ui.states.NotesTakerUiState
import com.betrend.cp.thenotes.utils.getDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class NotesTakerViewModel(private val repository: NotesRepository): ViewModel() {

    private val _uiState= MutableStateFlow(NotesTakerUiState())
    val uiState = _uiState.asStateFlow()

    private val time = getDate()
    private var currentNoteId: Int? = null

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun onColorChange(color: String) {
        _uiState.update { it.copy(color = color) }
    }

    fun loadNote(id: Int) {
        viewModelScope.launch {
            currentNoteId = id
            val note = repository.getNoteById(id)
            note?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        name = it.name,
                        content = it.content,
                        time = it.time,
                        isPinned = it.isPinned,
                        color = it.color
                    )
                }
                repository.update(note)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveNote() {
        viewModelScope.launch {
            val currentState = uiState.value
            val note = if (currentNoteId != null) {
                // Se tiver um ID, editar nota existente
                Note(
                    id = currentNoteId!!,
                    name = currentState.name,
                    content = currentState.content,
                    time = time.toString(),
                    isPinned = currentState.isPinned,
                    color = currentState.color
                )
            } else {
                // Senão, criar uma nova nota
                Note(
                    name = currentState.name,
                    content = currentState.content,
                    time = time.toString(),
                    isPinned = currentState.isPinned,
                    color = currentState.color
                )
            }

            if (currentNoteId != null) {
                repository.update(note)
            } else {
                repository.save(note)
            }
        }
    }
}
