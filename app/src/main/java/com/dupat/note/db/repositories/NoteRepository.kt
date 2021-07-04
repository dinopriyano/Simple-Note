package com.dupat.note.db.repositories

import com.dupat.note.db.dao.NoteDao
import com.dupat.note.db.entities.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao) {

    val notes = dao.getAllNote()
    fun note(noteID: Int) = dao.getNote(noteID)

    suspend fun insertNote(note: Note){
        dao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        dao.deleteNote(note)
    }

    suspend fun deleteAllNote(){
        dao.deleteAllNote()
    }

}