package com.example.cryptoapp.features.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    val viewState =  MutableLiveData<WalletFragment.State>()

    val assets = repository.getAllAssets()

    private fun formattedValue(value: Double): String {
        return String.format("%,.2f", value)
    }
}