package com.dupat.note.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dupat.note.R
import com.dupat.note.databinding.FragmentWriteBinding
import com.dupat.note.db.entities.Note
import com.dupat.note.ui.listener.PriorityDialogListener
import com.dupat.note.ui.utils.PriorityDialog
import com.dupat.note.ui.utils.priorityColor
import com.dupat.note.ui.utils.toast
import com.dupat.note.ui.viewmodel.NoteViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WriteFragment : Fragment(),View.OnClickListener, PriorityDialogListener {

    @Inject
    lateinit var disposable: CompositeDisposable
    private lateinit var binding:FragmentWriteBinding
    private lateinit var writeTime: Date
    private var writePriority: Int = 3
    private var writeID: Int = 0
    private val viewModel: NoteViewModel by activityViewModels()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy hh:mm a | ")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

        setupStream()
    }

    private fun setupStream(){
        val titleStream = RxTextView.textChanges(binding.etTitle)
            .map{
                it.length
            }

        val descriptionStream = RxTextView.textChanges(binding.etNote)
            .map{
                it.length
            }

        val combineStream = Observable.combineLatest(
            titleStream,
            descriptionStream,
            BiFunction { t1, t2 -> t1 + t2
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.txtTotalCharacters.text = "$it characters"
            }

        disposable.add(combineStream)
    }

    private fun setupDefaultData(){
        if(requireArguments() != null && WriteFragmentArgs.fromBundle(requireArguments()).isWrite){
            writeTime = Date()
            writePriority = 3
            writeID = 0
            binding.txtDateCreate.text = dateFormat.format(writeTime)
            binding.etNote.requestFocus()
        }
        else{
            val noteID = WriteFragmentArgs.fromBundle(requireArguments()).noteId
            viewModel.getNote(noteID).observe(requireActivity(), androidx.lifecycle.Observer {
                writeTime = it.created_at
                writePriority = it.priority
                writeID = it.id
                binding.txtDateCreate.text = dateFormat.format(it.created_at)
                binding.etTitle.setText(it.title)
                binding.etNote.setText(it.description)
                binding.cvPriority.setCardBackgroundColor(requireContext().priorityColor(writePriority))
            })
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack -> {
                requireActivity().onBackPressed()
            }
            R.id.btnSave -> {
                PriorityDialog(this,writePriority).show(requireActivity().supportFragmentManager,"PriorityDialog")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupDefaultData()
    }

    override fun onSave(priority: Int) {
        val note: Note = Note(
            id = writeID,
            title = if(binding.etTitle.text.toString().trim().isNullOrEmpty()) "Title" else binding.etTitle.text.toString().trim(),
            description = binding.etNote.text.toString().trim(),
            priority = priority,
            created_at = writeTime,
            updated_at = null
        )
        viewModel.insert(note)
        changePriority(priority)
    }

    private fun changePriority(priority: Int) {
        binding.cvPriority.setCardBackgroundColor(requireContext().priorityColor(priority))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}