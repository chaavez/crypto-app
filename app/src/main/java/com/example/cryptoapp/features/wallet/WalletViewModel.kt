package com.example.cryptoapp.features.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    data class AssetItem(
        val assetEntity: AssetEntity
    )

    private val _assets: Flow<List<AssetItem>> = repository.getAllAssets().map {
        it.map { asset ->
            AssetItem(
                asset.copy(
                    variation = "${formattedValue(asset.variation.toDouble())}%",
                    price = formattedValue(asset.price.toDouble()),
                    totalInvestment = formattedValue(asset.totalInvestment.toDouble()),
                    totalInvestmentAsset = formattedValue(asset.totalInvestmentAsset.toDouble())
                )
            )
        }
    }

    val assets: Flow<List<AssetItem>> = _assets

    private fun formattedValue(value: Double): String {
        return String.format("%,.2f", value)
    }

    suspend fun totalAssetsInvestment() {
        viewModelScope.launch {
            repository.updateTotalInvestment()
        }
    }
}