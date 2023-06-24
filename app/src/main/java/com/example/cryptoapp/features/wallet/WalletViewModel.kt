package com.example.cryptoapp.features.wallet

import androidx.lifecycle.*
import androidx.room.Transaction
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import com.example.cryptoapp.features.simulator.AssetPricesFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    val viewState =  MutableLiveData<WalletFragment.State>()

    private val _assets: MutableLiveData<List<AssetEntity>> = MutableLiveData()
    val assets: LiveData<List<AssetEntity>> = _assets

    val assetPricesFormatter =  MutableLiveData<WalletFormatter>()

    fun loadAssets() {
        viewModelScope.launch {
            val assetsList = repository.getAllAssets().first()
            if(assetsList.isEmpty()) {
                viewState.value = WalletFragment.State.EMPTY
            } else {
                _assets.postValue(assetsList)
                assetPricesFormatter.value = WalletFormatter(assetsList)
                viewState.value = WalletFragment.State.CONTENT
            }
        }
    }
}

class WalletFormatter(
    var totalInvested: String,
    var totalToday: String,
    var totalProfit: String,
    var variationPercentage: String
) {
    enum class ResultType {
        POSITIVE,
        NEGATIVE,
        SAME
    }

    constructor(assetsEntity: List<AssetEntity>) : this(
        "",
        "",
        "",
        "",
    ){
        calculateTotalInvested(assetsEntity)
        calculateTotalToday(assetsEntity)
        calculateTotalProfit(assetsEntity)
        calculateVariation(assetsEntity)
    }

    private fun calculateTotalInvested(assetsEntities: List<AssetEntity>) {
        val totalInvested = assetsEntities.sumOf {
            it.totalInvestmentAsset.toDouble()
        }
        this.totalInvested = "R$ ${String.format("%,.2f", totalInvested)}"
    }

    private fun calculateTotalToday(assetsEntities: List<AssetEntity>) {
        val totalToday = assetsEntities.sumOf {
            it.price.toDouble()
        }
        this.totalToday =  "R$ ${String.format("%,.2f", totalToday)}"
    }

    private fun calculateTotalProfit(assetsEntities: List<AssetEntity>) {
        val totalPrice = assetsEntities.sumOf {
            it.totalInvestmentAsset.toDouble()
        }
        val totalToday = assetsEntities.sumOf {
            it.price.toDouble()
        }
        this.totalProfit = "R$ ${String.format("%,.2f", (totalPrice - totalToday))}"
    }

    private fun calculateVariation(assetsEntities: List<AssetEntity>) {
        val totalInvested = assetsEntities.sumOf {
            it.totalInvestmentAsset.toDouble()
        }
        val currencyPrice = assetsEntities.sumOf {
         it.price.toDouble()
        }
        this.variationPercentage = "${String.format("%,.0f", (totalInvested / currencyPrice) * 100)}%"
    }

//    private fun setupResultType(oldAsset: AssetEntity, currentAsset: AssetEntity) {
//        val difference = currentAsset.price.toDouble() - oldAsset.price.toDouble()
//
//        resultType = when {
//            difference == 0.0 -> ResultType.SAME
//            difference > 0.0 -> ResultType.POSITIVE
//            else -> ResultType.NEGATIVE
//        }
//    }
}