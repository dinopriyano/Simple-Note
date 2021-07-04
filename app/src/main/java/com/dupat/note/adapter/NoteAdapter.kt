package com.dupat.note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dupat.note.R
import com.dupat.note.databinding.ItemNoteListBinding
import com.dupat.note.db.entities.Note

class NoteAdapter(private val notes: List<Note> = ArrayList<Note>()) : RecyclerView.Adapter<NoteAdapter.ViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewModel {
        val binding = ItemNoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewModel(binding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewModel, position: Int) {
        val note = notes.get(position)
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    class ViewModel(private val binding: ItemNoteListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note){
            with(binding){
                txtTitle.text = note.title
                txtDesc.text = note.description

                cvPriority.setCardBackgroundColor(
                    when(note.priority){
                        1 -> itemView.context.resources.getColor(R.color.red)
                        2 -> itemView.context.resources.getColor(R.color.orange)
                        else -> itemView.context.resources.getColor(R.color.green)
                    }
                )
            }
        }
    }
}