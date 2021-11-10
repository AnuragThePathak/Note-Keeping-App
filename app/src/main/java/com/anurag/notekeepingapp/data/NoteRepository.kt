package com.anurag.notekeepingapp.data

import androidx.annotation.WorkerThread
import javax.inject.Inject
import javax.inject.Singleton

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    @WorkerThread
    suspend fun initializeNote(noteId: Int): Note =
        if (noteId == -1) Note()
        else noteDao.getNoteById(noteId)

    @WorkerThread
    suspend fun submit(note: Note, noteId: Int): Int =
        if (note.title != "" || note.description != "") {
            if (noteId == -1) {
                note.id = noteDao.insert(note).toInt()
                note.id
            } else {
                noteDao.update(note)
                noteId
            }
        } else {
            noteDao.delete(note)
            note.id = 0
            -1
        }

    fun delete(note: Note): Int {
        note.title = ""
        note.description = ""
        return -1
    }

    fun getAllNotes() = noteDao.loadAllNotes()
}