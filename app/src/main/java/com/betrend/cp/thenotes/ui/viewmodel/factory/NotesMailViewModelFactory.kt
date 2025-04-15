package com.betrend.cp.thenotes.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betrend.cp.thenotes.data.local.dao.NotesDao
import com.betrend.cp.thenotes.data.remote.auth.GoogleAuthManager
import com.betrend.cp.thenotes.data.remote.auth.GoogleDriveServiceHelper
import com.betrend.cp.thenotes.ui.viewmodel.NotesMailViewModel

// Crie um único ViewModelFactory
class NotesMailViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val context = context.applicationContext
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesMailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesMailViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}