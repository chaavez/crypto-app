package com.example.cryptoapp.features.simulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.common.fragments.highlights.HighlightsRepository
import com.example.cryptoapp.common.fragments.highlights.HighlightsViewModel

class SimulatorViewModelFactory(private val repository: SimulatorRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimulatorViewModel::class.java)) {
            return SimulatorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown SimulatorViewModel class")
    }
}