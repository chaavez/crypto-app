package com.example.cryptoapp.common.fragments.highlights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HighlightsViewModelFactory(private val repository: HighlightsRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighlightsViewModel::class.java)) {
            return HighlightsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown HighlightsViewModel class")
    }
}