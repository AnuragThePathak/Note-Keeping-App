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
        if (noteId == -1) Note(title = "")
        else noteDao.getNoteById(noteId)

    @WorkerThread
    suspend fun submit(note: Note, noteId: Int) =
        if (note.title != "") {
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

    @WorkerThread
    suspend fun delete(note: Note) = noteDao.delete(note)

    fun getAllNotes() = noteDao.loadAllNotes()
}