package com.anurag.notekeepingapp.di

import android.content.Context
import com.anurag.notekeepingapp.data.NoteDao
import com.anurag.notekeepingapp.data.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDataBase {
        return NoteDataBase.getDatabase(context)
    }

    @Provides
    fun provideNoteDao(database: NoteDataBase): NoteDao {
        return database.noteDao()
    }
}