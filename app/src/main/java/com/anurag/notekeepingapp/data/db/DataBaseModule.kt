package com.anurag.notekeepingapp.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDataBase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNoteDao(database: NoteDataBase): NoteDao {
        return database.noteDao()
    }
}