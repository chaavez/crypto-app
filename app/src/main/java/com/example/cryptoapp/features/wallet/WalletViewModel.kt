package com.example.cryptoapp.features.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.database.repository.AssetEntityRepository

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    val viewState =  MutableLiveData<WalletFragment.State>()

    val assets = repository.getAllAssets()

    private fun formattedValue(value: Double): String {
        return String.format("%,.2f", value)
    }
}