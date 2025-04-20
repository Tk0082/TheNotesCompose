package com.betrend.cp.thenotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.betrend.cp.thenotes.data.local.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    /* Atualizar notas já criadas */
    @Update //@Query("UPDATE notesdb SET name = :name, content = :content WHERE ID = :id")
    suspend fun update(note: Note)

    /* Pegar todas as Notas Pinadas e não Pinadas*/
    @Query("SELECT * FROM notesdb ORDER BY id ASC")
    suspend fun getAllNotes(): List<Note>

    /* Pegar todas as Notas não Pinadas*/
    @Query("SELECT * FROM notesdb WHERE isPinned == 0 ORDER BY id ASC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notesdb WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    /* Deletar Notas */
    @Query("DELETE FROM notesdb WHERE id = :id")
    suspend fun deleteNote(id: Int)

    /* Fixar Notas */
    @Query("UPDATE notesdb SET isPinned = :pin WHERE ID = :id")
    suspend fun pin(id: Int, pin: Boolean)

    /* Pesquisa de NOTAS Pinadas */
    @Query("SELECT * FROM notesdb WHERE isPinned == 1")
    fun getPinned(): Flow<List<Note>>

    /* Pesquisar notas */
    @Query("SELECT * FROM notesdb WHERE name LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<Note>>

}