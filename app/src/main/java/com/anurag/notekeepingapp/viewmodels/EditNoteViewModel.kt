package com.anurag.notekeepingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.notekeepingapp.data.Note
import com.anurag.notekeepingapp.data.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var noteId = savedStateHandle.get<Int>("note_id")!!
    val id get() = noteId
    var note = MutableLiveData<Note>()

    val noteText get() = "${note.value?.title}\n${note.value?.description}"

    /* 1. Initializing this Mutable Live Data doesn't initialize note.value.
       2. If we make note.value!!.title empty, it still doesn't become null.
       3. Rather it becomes "" (i.e. String of zero length).
     */

    init {
        viewModelScope.launch(Dispatchers.IO) {
            note.postValue(repository.initializeNote(noteId))
        }
    }

    fun submitNote() = viewModelScope.launch(Dispatchers.IO) {
        noteId = repository.submit(note.value!!, noteId)
        savedStateHandle["note_id"] = noteId
    }

    fun deleteNote() {
        noteId = repository.delete(note.value!!)
    }
}