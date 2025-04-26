package com.betrend.cp.thenotes.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betrend.cp.thenotes.data.local.NotesDatabase.Companion.getNotes
import com.betrend.cp.thenotes.ui.viewmodel.NotesMailViewModel

class NotesMailViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val context = context.applicationContext
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesMailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesMailViewModel(context, getNotes(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}