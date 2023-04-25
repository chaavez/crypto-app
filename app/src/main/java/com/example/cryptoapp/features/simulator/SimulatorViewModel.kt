package com.example.cryptoapp.features.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.R

class SimulatorViewModel : ViewModel() {
    private val _saveButtonColor = MutableLiveData<Int>()
    val saveButtonColor: LiveData<Int> = _saveButtonColor

    init {
        _saveButtonColor.value = R.color.primary_300
    }

    fun saveInWallet(amount: String, date: String) {
        if(amount.isNotEmpty() && date.isNotEmpty()) {
            _saveButtonColor.value = R.color.secondary_200
        } else {
            _saveButtonColor.value = R.color.primary_300
        }
    }
}