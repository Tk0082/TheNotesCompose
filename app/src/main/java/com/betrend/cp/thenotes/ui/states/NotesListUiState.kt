package com.betrend.cp.thenotes.ui.states

import com.betrend.cp.thenotes.data.local.entities.Note

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val onNotesChange: (Note) -> Unit = {},
    val isPinned: (Note) -> Unit = {},
    val isLoading: Boolean = false,
    val error: String? =null
)
