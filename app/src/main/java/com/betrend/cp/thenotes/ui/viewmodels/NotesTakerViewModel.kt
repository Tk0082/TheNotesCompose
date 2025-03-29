package com.betrend.cp.thenotes.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betrend.cp.thenotes.di.repositories.NotesRepository
import com.betrend.cp.thenotes.entities.Note
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

    val time = getDate()
    private val pin = false

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun loadNote(id: Int) {
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            note?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        name = it.name,
                        content = it.content,
                        time = it.time,
                        isPinned = it.isPinned
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
            val note = Note(
                name = currentState.name,
                content = currentState.content,
                time = time.toString(),
                isPinned = currentState.isPinned
            )
            repository.save(note)
        }
    }
}
