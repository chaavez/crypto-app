package com.example.cryptoapp.features.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimulatorViewModel() : ViewModel() {
    private val _isTextViewFilled = MutableLiveData<Boolean>()
    val isTextViewFilled: LiveData<Boolean>
        get() = _isTextViewFilled

    fun checkTextView(text: String) {
        _isTextViewFilled.value = !text.isNullOrEmpty()
    }
}