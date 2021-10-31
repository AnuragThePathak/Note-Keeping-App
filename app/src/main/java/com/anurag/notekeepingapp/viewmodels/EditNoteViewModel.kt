package com.anurag.notekeepingapp.viewmodels

import androidx.lifecycle.*
import com.anurag.notekeepingapp.data.Note
import com.anurag.notekeepingapp.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId = savedStateHandle.getLiveData<Int>("note_id")
    var note = MutableLiveData<Note>()

    init {
        if (noteId.value != -1) note = repository.getNoteById(noteId.value!!)
            .asLiveData() as MutableLiveData<Note>
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
}