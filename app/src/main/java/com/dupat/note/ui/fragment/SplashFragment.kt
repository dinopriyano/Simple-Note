package com.dupat.note.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.dupat.note.R
import com.dupat.note.ui.utils.navOption

class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            findNavController().navigate(action,R.id.splashFragment.navOption())
        }, 2000)
    }
}