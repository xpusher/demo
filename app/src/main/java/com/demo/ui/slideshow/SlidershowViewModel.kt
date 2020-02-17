package com.demo.ui.slideshow

import android.os.Parcelable
import android.util.SparseArray
import androidx.lifecycle.ViewModel

class SlidershowViewModel : ViewModel() {
    val state: SparseArray<Parcelable> = SparseArray()
}