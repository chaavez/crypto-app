package com.example.cryptoapp.features.simulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SimulatorViewModel(private val repository: SimulatorRepository): ViewModel() {
    val filledField = MutableLiveData<Boolean>().apply { value = false }

//    fun buttonColor(amount: String, date: String): Int {
//        return if (filledField.value == true) {
//            R.color.primary_300
//        } else {
//            R.color.secondary_200
//        }
//    }
}