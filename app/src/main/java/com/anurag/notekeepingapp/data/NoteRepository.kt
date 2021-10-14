package com.anurag.notekeepingapp.data

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDao: NoteDao) {
    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun update(note: Note) = noteDao.update(note)

    fun getNoteById(id: Int) = noteDao.getNoteById(id)

    fun getAllNotes() = noteDao.loadAllNotes()
}