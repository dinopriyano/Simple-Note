package com.dupat.note.ui.viewmodel

import android.util.Log
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

    val getAllNote = repository.getAllNote

    fun getNote(noteID: Int) = repository.getNote(noteID)

    fun searchNote(keyword: String) = repository.searchNote(keyword)

    fun insert(note: Note){
        viewModelScope.launch {
            try{
                repository.insertNote(note)
                Log.d("ViewModelBisa", "Bisa gan")
            }
            catch (e: Exception){
                Log.d("ViewModelError", "Error: $e")
            }
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