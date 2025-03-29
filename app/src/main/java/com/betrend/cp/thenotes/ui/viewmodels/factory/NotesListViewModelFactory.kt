package com.betrend.cp.thenotes.ui.viewmodels.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betrend.cp.thenotes.di.repositories.NotesRepository
import com.betrend.cp.thenotes.ui.viewmodels.NotesListViewModel

@RequiresApi(Build.VERSION_CODES.O)
class NotesListViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
