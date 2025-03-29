package com.betrend.cp.thenotes.ui.viewmodels.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betrend.cp.thenotes.di.repositories.NotesRepository
import com.betrend.cp.thenotes.ui.viewmodels.NotesTakerViewModel

@Suppress("UNCHECKED_CAST")
@RequiresApi(Build.VERSION_CODES.O)
class NotesTakerViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesTakerViewModel::class.java)) {
            return NotesTakerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
