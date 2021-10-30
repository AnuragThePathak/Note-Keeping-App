package com.anurag.notekeepingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anurag.notekeepingapp.data.Note

// Annotates class to be a Room Database with a table (entity) of the Note class
@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}

