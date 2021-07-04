package com.dupat.note.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.dupat.note.R
import com.dupat.note.databinding.FragmentWriteBinding
import com.dupat.note.utils.navOption
import com.dupat.note.utils.showKeyboard

class WriteFragment : Fragment(),View.OnClickListener {

    private lateinit var binding:FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack -> {
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etNote.requestFocus()
    }
}