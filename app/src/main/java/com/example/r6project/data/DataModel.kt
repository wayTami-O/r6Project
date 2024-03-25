package com.example.r6project.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val message: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val rang: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}