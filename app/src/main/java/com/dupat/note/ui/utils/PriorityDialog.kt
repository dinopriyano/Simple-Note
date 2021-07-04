package com.dupat.note.ui.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dupat.note.R
import com.dupat.note.databinding.LayoutPriorityDialogBinding
import com.dupat.note.ui.listener.PriorityDialogListener
import worker8.com.github.radiogroupplus.RadioGroupPlus

class PriorityDialog(val priorityListener: PriorityDialogListener, val defaultPriority: Int): DialogFragment(), View.OnClickListener, RadioGroupPlus.OnCheckedChangeListener {

    private lateinit var binding: LayoutPriorityDialogBinding
    private var dialogPriority: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = LayoutPriorityDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialogPriority = defaultPriority
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.cvHigh.setOnClickListener(this)
        binding.cvMedium.setOnClickListener(this)
        binding.cvNormal.setOnClickListener(this)
        binding.radioGroupPlus.setOnCheckedChangeListener(this)

        setDefaultCheck()
    }

    private fun setDefaultCheck(){
        when(defaultPriority){
            1 -> {
                binding.rbHigh.isChecked = true
            }
            2 -> {
                binding.rbMedium.isChecked = true
            }
            else -> {
                binding.rbNormal.isChecked = true
            }
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnSave -> {
                priorityListener.onSave(dialogPriority)
            }
            R.id.btnCancel -> {
                dialog?.dismiss()
            }
            R.id.cvHigh -> {
                binding.rbHigh.isChecked = true
            }
            R.id.cvMedium -> {
                binding.rbMedium.isChecked = true
            }
            R.id.cvNormal -> {
                binding.rbNormal.isChecked = true
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroupPlus?, checkedId: Int) {
        when(checkedId){
            R.id.rbHigh -> {
                dialogPriority = 1
            }
            R.id.rbMedium -> {
                dialogPriority = 2
            }
            R.id.rbNormal -> {
                dialogPriority = 3
            }
        }
    }

}