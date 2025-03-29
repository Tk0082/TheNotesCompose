package com.betrend.cp.thenotes.ui.states

data class NotesTakerUiState(
    val name: String = "",
    val content: String = "",
    val time: String = "",
    val isPinned: Boolean = false,
    val onNameChange: (String) -> Unit = {},
    val onContentChange: (String) -> Unit = {}
)
