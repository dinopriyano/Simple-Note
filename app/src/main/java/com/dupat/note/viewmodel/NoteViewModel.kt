package com.dupat.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dupat.note.db.entities.Note
import com.dupat.note.db.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {

    val notes = MutableLiveData<List<Note>>()

    fun getNotes(){
        viewModelScope.launch {
            notes.postValue(repository.notes.value)
        }
    }

    fun showNote(noteID: Int) : LiveData<Note>{
        return repository.note(noteID)
    }

    fun insert(note: Note){
        viewModelScope.launch {
            repository?.insertNote(note)
        }
    }

    fun update(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAllNote()
        }
    }

}