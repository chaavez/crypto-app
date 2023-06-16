package com.example.cryptoapp.features.wallet

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import kotlinx.coroutines.flow.Flow

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    private val _assets: Flow<List<AssetEntity>> = repository.getAllAssets()
    val assets: Flow<List<AssetEntity>> = _assets
}