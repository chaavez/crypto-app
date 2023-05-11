package com.example.cryptoapp.features.searchAsset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchAssetViewModelFactory(private val repository: SearchAssetRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchAssetViewModel::class.java)) {
            return SearchAssetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown MostValuedViewModel class")
    }
}