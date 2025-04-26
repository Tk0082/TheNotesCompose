package com.betrend.cp.thenotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.betrend.cp.thenotes.data.local.dao.NotesDao
import com.betrend.cp.thenotes.data.local.entities.Note
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Adicione a nova coluna
        db.execSQL("ALTER TABLE notesdb ADD COLUMN color TEXT NOT NULL DEFAULT 'FFEB3B'")
    }
}

@Database(entities = [Note::class], version = 2, exportSchema = false)
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
                )
                    .addMigrations(MIGRATION_1_2)
                    .build().also { INSTANCE = it }
            }
        }
    }
}
