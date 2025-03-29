package com.betrend.cp.thenotes.database

import android.content.Context
import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Provides
//    @Singleton
    fun provideNotesDao(database: NotesDatabase): NotesDao = database.notesDao()

//    @Provides
//    @Singleton
    fun provideNotesDatabase(context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notesdb"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
