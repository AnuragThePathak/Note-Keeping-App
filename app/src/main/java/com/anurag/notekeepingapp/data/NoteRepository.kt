package com.anurag.notekeepingapp.data

import androidx.annotation.WorkerThread
import javax.inject.Inject
import javax.inject.Singleton

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    @WorkerThread
    suspend fun insert(note: Note) = noteDao.insert(note)

    @WorkerThread
    suspend fun delete(note: Note) = noteDao.delete(note)

    @WorkerThread
    suspend fun update(note: Note) = noteDao.update(note)

    fun getNoteById(id: Int) = noteDao.getNoteById(id)

    fun getAllNotes() = noteDao.loadAllNotes()
}