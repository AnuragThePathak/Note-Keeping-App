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

    private val noteId = savedStateHandle.getLiveData<Int>("note_id")
    var note = MutableLiveData<Note>()
    /* 1. Initializing this Mutable Live Data doesn't initialize note.value.
       2. If we make note.value!!.title empty, it still doesn't become null.
       3. Rather it becomes "" (i.e. String of zero length).
     */

    init {
        note.value = Note(title = "")

        if (noteId.value != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                note.postValue(repository.getNoteById(noteId.value!!))
            }

//            noteId.value = note.value!!.id
//            println(noteId.value)
        }
    }

    fun submit() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.finalSubmit(note.value!!)
        }
    }

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note.value!!)
        }
    }
}