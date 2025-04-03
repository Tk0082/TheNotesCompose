package com.betrend.cp.thenotes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesdb")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val content: String,
    val time: String,
    val isPinned: Boolean
)
