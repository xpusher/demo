package com.demo.ui.common


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.demo.common.BaseFragment

import com.demo.R

/**
 * A simple [Fragment] subclass.
 */
class EmptyContentFragment : BaseFragment() {
    lateinit var btnHome: Button
    lateinit var btnExit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_empty_content, container, false)
        btnHome=root.findViewById(R.id.id005)
        btnExit=root.findViewById(R.id.id006)

        return root
    }

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        btnHome.setOnClickListener {
            sharedViewModel.dataSlider.value=null
            findNavController().navigate(R.id.action_nav_empty_content_to_nav_home)
        }
        btnExit.setOnClickListener { activity?.finish() }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home) {
            sharedViewModel.dataSlider.value=null
        }
        return super.onOptionsItemSelected(item)
    }

}
