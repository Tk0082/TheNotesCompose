package com.betrend.cp.thenotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.betrend.cp.thenotes.entities.Note
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getNotes(context: Context): NotesDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notesdb"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
