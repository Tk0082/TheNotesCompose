package com.betrend.cp.thenotes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val content: String,
    val isPinned: Boolean = false
)
