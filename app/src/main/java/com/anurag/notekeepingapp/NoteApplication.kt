package com.anurag.notekeepingapp

import android.app.Application
import com.anurag.notekeepingapp.data.NoteDataBase
import com.anurag.notekeepingapp.data.NoteRepository

class NoteApplication: Application() {
    private val dataBase by lazy { NoteDataBase.getDatabase(this) }
    val repository by lazy { NoteRepository(dataBase.noteDao()) }
}