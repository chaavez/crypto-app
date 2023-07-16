package com.example.cryptoapp.features.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.database.repository.AssetEntityRepository

class WalletViewModelFactory(private val repository: AssetEntityRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown MostValuedViewModel class")
    }
}