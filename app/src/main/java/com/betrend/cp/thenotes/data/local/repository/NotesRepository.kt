package com.betrend.cp.thenotes.data.local.repository

import com.betrend.cp.thenotes.data.local.dao.NotesDao
import com.betrend.cp.thenotes.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

class NotesRepository (private val dao: NotesDao){

    fun getAllNotes(): Flow<List<Note>> = dao.getAll()

    suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    suspend fun save(note: Note) = dao.insert(note)

    suspend fun update(note: Note) = dao.update(note)

    suspend fun delete(note: Note) = dao.deleteNote(note.id)

    suspend fun pin(note: Note) = dao.pin(note.id, !note.isPinned)

    fun pinned(): Flow<List<Note>> = dao.getPinned()

    fun searchNotes(note: String): Flow<List<Note>> = dao.searchNotes(note)
}