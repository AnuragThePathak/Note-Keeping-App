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
        if (noteId.value == -1) note.value = Note(title = "")
        else {
            viewModelScope.launch(Dispatchers.IO) {
                note.postValue(repository.getNoteById(noteId.value!!))
            }
        }
    }

    fun update() = viewModelScope.launch(Dispatchers.IO) {
        if (noteId.value == -1) {
            val key = repository.insert(note.value!!).toInt()
            noteId.postValue(key)
            note.value!!.id = key
        } else {
            repository.update(note.value!!)
        }
    }


    fun submit() {
        if (note.value!!.title == "") {
            viewModelScope.launch(Dispatchers.IO) {
                repository.delete(note.value!!)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println(note.value)
    }
}