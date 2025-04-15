package com.betrend.cp.thenotes.data.remote.drive.backup

import com.betrend.cp.thenotes.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

data class NotesBackup(
    val version: Int = 1,
    val timestamp: Long = System.currentTimeMillis(),
    val notes: Flow<Note>
)