package com.demo.ui.slideshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.demo.common.BaseFragment

import com.demo.R

/**
 * A simple [Fragment] subclass.
 */
class SlideshowRequestFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slideshow_request, container, false)
    }

    override fun onStart() {
        super.onStart()
        sharedViewModel.dataSlider.observe(this, Observer { t ->
            t?.let {
                view?.findNavController()?.popBackStack()
            }
        })
        sharedViewModel.request()

    }

    override fun onDestroyView() {
        sharedViewModel.cancel()
        super.onDestroyView()
    }

}
