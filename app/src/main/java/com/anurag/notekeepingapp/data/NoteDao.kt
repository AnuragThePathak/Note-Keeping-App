package com.anurag.notekeepingapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note

    @Query("SELECT * FROM note_table")
    fun loadAllNotes(): Flow<List<Note>>
}