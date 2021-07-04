package com.dupat.note.di.module

import android.app.Application
import android.content.Context
import com.dupat.note.db.NoteDatabase
import com.dupat.note.db.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun getNoteDatabase(app: Application): NoteDatabase{
        return NoteDatabase.getInstance(app)
    }

    @Singleton
    @Provides
    fun getDao(noteDB: NoteDatabase): NoteDao{
        return noteDB.noteDao()
    }

}