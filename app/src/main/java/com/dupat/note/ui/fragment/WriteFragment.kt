package com.dupat.note.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dupat.note.R
import com.dupat.note.databinding.FragmentWriteBinding
import com.dupat.note.ui.listener.PriorityDialogListener
import com.dupat.note.ui.utils.PriorityDialog
import com.dupat.note.ui.utils.toast
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class WriteFragment : Fragment(),View.OnClickListener, PriorityDialogListener {

    private lateinit var binding:FragmentWriteBinding

    @Inject
    lateinit var disposable: CompositeDisposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

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

        disposable.addAll(combineStream)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack -> {
                requireActivity().onBackPressed()
            }
            R.id.btnSave -> {
                PriorityDialog(this,3).show(requireActivity().supportFragmentManager,"PriorityDialog")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etNote.requestFocus()
    }

    override fun onSave(priority: Int) {
        requireContext().toast(priority.toString())
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}