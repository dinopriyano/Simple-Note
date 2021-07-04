package com.dupat.note.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dupat.note.db.entities.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteAnyNote(notes: List<Note>)

    @Query("DELETE FROM note")
    suspend fun deleteAllNote()

    @Query("SELECT * FROM note WHERE note_id=:noteID")
    fun getNote(noteID: Int) : LiveData<Note>

    @Query("SELECT * FROM note")
    fun getAllNote() : LiveData<List<Note>>
}