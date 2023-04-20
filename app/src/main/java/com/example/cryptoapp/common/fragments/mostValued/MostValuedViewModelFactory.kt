package com.example.cryptoapp.common.fragments.mostValued

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MostValuedViewModelFactory(private val repository: MostValuedRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MostValuedViewModel::class.java)) {
            return MostValuedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown MostValuedViewModel class")
    }
}