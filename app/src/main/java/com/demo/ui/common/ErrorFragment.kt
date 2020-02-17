package com.demo.ui.common


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.demo.common.BaseFragment

import com.demo.R
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class ErrorFragment : BaseFragment() {
    lateinit var btnHome:Button
    lateinit var btnExit:Button
    lateinit var tvMore:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_error, container, false)
        btnHome=root.findViewById(R.id.id002)
        btnExit=root.findViewById(R.id.id003)
        tvMore=root.findViewById(R.id.id004)
        return root
    }

    override fun onStart() {
        super.onStart()
        btnHome.setOnClickListener {
            sharedViewModel.dataSlider.value=null
            findNavController().navigate(R.id.action_nav_error_to_nav_home)
        }
        btnExit.setOnClickListener { activity?.finish() }
        tvMore.setOnClickListener {
            arguments?.getSerializable(Exception::class.java.simpleName)?.let {
                tvMore.text=it.toString()
            }?: kotlin.run { tvMore.text=getString(android.R.string.unknownName) }
        }

    }

    override fun onDestroy() {
        sharedViewModel.exception.value=null
        super.onDestroy()
    }
}
