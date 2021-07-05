package com.dupat.note.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dupat.note.R
import com.dupat.note.databinding.ItemNoteListBinding
import com.dupat.note.db.entities.Note
import com.dupat.note.ui.listener.NoteItemListener
import com.dupat.note.ui.utils.priorityColor

class NoteAdapter(private val notes: List<Note> = ArrayList<Note>(), private val noteItemListener: NoteItemListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val binding = ItemNoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, noteItemListener, notes)
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        val note = notes.get(position)
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    class ViewHolder(private val binding: ItemNoteListBinding, private val noteItemListener: NoteItemListener, private val notes: List<Note>): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(note: Note){
            with(binding){
                txtTitle.text = note.title
                txtDesc.text = note.description
                cvPriority.setCardBackgroundColor(itemView.context.priorityColor(note.priority))

                binding.cvNote.setOnClickListener(this@ViewHolder)
            }
        }

        override fun onClick(v: View?) {
            val note = notes[adapterPosition]

            when(v?.id){
                R.id.cvNote -> {
                    noteItemListener.OnNoteClick(note.id)
                }
            }
        }
    }
}