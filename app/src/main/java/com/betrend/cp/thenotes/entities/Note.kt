package com.betrend.cp.thenotes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notesdb")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val content: String,
    val time: String,
    val isPinned: Boolean
)
