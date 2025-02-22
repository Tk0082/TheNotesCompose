package com.betrend.cp.thenotes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.betrend.cp.thenotes.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}