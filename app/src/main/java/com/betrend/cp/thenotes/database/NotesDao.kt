package com.betrend.cp.thenotes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.betrend.cp.thenotes.entities.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    /* Pegar todas as Notas não Pinadas*/
    @Query("SELECT * FROM Note WHERE isPinned == 0")
    fun getAll(): List<Note>

    /* Deletar Notas */
    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteNote(id: Int)

    /* Fixar Notas */
    @Query("UPDATE Note SET isPinned = :pin WHERE ID = :id")
    fun pin(id: Int, pin: Boolean)

    /* Pesquisa de NOTAS Pinadas */
    @Query("SELECT * FROM note WHERE isPinned == 1")
    fun getPinned(): List<Note>
}