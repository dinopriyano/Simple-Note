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
import com.dupat.note.ui.listener.NoteItemListener
import com.dupat.note.ui.viewmodel.NoteViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),View.OnClickListener, NoteItemListener {

    @Inject
    lateinit var disposable: CompositeDisposable
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
        handleSearchNote()
    }

    private fun showNotes(){
        binding.rvNote.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.getAllNote.observe(requireActivity(), Observer {
            populateAdapter(it)
        })

    }

    private fun populateAdapter(notes: List<Note>){
        if(notes.isNotEmpty()){
            adapter = NoteAdapter(notes, this)
            binding.containerEmpty.visibility = View.GONE
        }
        else{
            adapter = NoteAdapter(ArrayList<Note>(), this)
            binding.containerEmpty.visibility = View.VISIBLE
        }

        binding.rvNote.adapter = adapter
    }

    private fun handleSearchNote(){
        val searchStream = RxTextView.textChanges(binding.etSearch)
            .map {
                it.toString()
            }
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { it ->
                viewModel.searchNote(it).observe(requireActivity(), Observer {
                    populateAdapter(it)
                })
            }

        disposable.add(searchStream)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnAddNote -> {
                val action = HomeFragmentDirections.actionHomeFragmentToWriteFragment(true)
                findNavController().navigate(action)
            }
        }
    }

    override fun OnNoteClick(noteID: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToWriteFragment(false,noteID)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}