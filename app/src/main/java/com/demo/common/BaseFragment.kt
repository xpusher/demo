package com.demo.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.MainActivity

open class BaseFragment :Fragment(){
    protected lateinit var sharedViewModel: SharedViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = ViewModelProvider(activity as MainActivity).get(SharedViewModel::class.java)
    }

}