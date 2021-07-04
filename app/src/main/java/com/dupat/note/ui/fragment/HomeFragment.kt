package com.dupat.note.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dupat.note.R
import com.dupat.note.ui.adapter.NoteAdapter
import com.dupat.note.databinding.FragmentHomeBinding
import com.dupat.note.db.entities.Note
import com.dupat.note.ui.utils.GridSpacingItemDecoration
import com.dupat.note.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddNote.setOnClickListener(this)

        showNotes()
        viewModel.getNotes()
    }

    private fun showNotes(){
        binding.rvNote.apply {
            addItemDecoration(GridSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.staggered_layout_margin)))
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            adapter = NoteAdapter(if(it == null) ArrayList<Note>() else it)
            binding.rvNote.adapter = adapter
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddNote -> {
                val action = HomeFragmentDirections.actionHomeFragmentToWriteFragment()
                findNavController().navigate(action)
            }
        }
    }
}