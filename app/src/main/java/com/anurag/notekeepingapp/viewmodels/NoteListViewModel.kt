package com.anurag.notekeepingapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.anurag.notekeepingapp.data.NoteEntity
import com.anurag.notekeepingapp.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes = repository.allNotes.asLiveData()

    fun delete(note: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
}

class NoteListViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}