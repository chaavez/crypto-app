package com.example.cryptoapp.features.wallet

import androidx.lifecycle.*
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    val viewState =  MutableLiveData<WalletFragment.State>()

    private val _assets: MutableLiveData<List<AssetEntity>> = MutableLiveData()
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
    var variationPercentage: String,
    var resultType: ResultType,
    var walletAssets: List<WalletAssetFormatter>
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
        ResultType.SAME,
        emptyList()
    ) {
        calculateTotalInvested(assetsEntity)
        calculateTotalToday(assetsEntity)
        calculateTotalProfit(assetsEntity)
        calculateVariation(assetsEntity)
        setupResultType(assetsEntity)
        walletAssetsFormatter(assetsEntity)
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
        this.totalToday = "R$ ${String.format("%,.2f", totalToday)}"
    }

    private fun calculateTotalProfit(assetsEntities: List<AssetEntity>) {
        val totalPrice = assetsEntities.sumOf {
            it.totalInvestmentAsset.toDouble()
        }
        val totalToday = assetsEntities.sumOf {
            it.price.toDouble()
        }
        var profit = totalToday - totalPrice

        if (profit < 0) {
            this.totalProfit = "R$ ${String.format("%,.2f", profit)}"
        } else {
            this.totalProfit = "R$ +${String.format("%,.2f", profit)}"
        }
    }

    private fun calculateVariation(assetsEntities: List<AssetEntity>) {
        val totalInvested = assetsEntities.sumOf {
            it.totalInvestmentAsset.toDouble()
        }
        val currencyPrice = assetsEntities.sumOf {
            it.price.toDouble()
        }
        var variation = ((currencyPrice - totalInvested) / totalInvested) * 100

        if (variation < 0) {
            this.variationPercentage = "-${String.format("%,.0f", -variation)}%"
        } else {
            this.variationPercentage = "${String.format("%,.0f", variation)}%"
        }
    }

    private fun setupResultType(assetsEntities: List<AssetEntity>) {
        val totalInvested = assetsEntities.sumOf { it.totalInvestmentAsset.toDouble() }
        val totalToday = assetsEntities.sumOf { it.price.toDouble() }
        val difference = totalToday - totalInvested

        resultType = when {
            difference == 0.0 -> ResultType.SAME
            difference > 0.0 -> ResultType.POSITIVE
            else -> ResultType.NEGATIVE
        }
    }

    private fun walletAssetsFormatter(assetsEntities: List<AssetEntity>) {
        val formattedAssets = assetsEntities.map {
            WalletAssetFormatter(it)
        }
        this.walletAssets = formattedAssets
    }
}

class WalletAssetFormatter(
    val symbol: String,
    val name: String,
    val icon: String,
    var totalAssetInvested: String,
    var currencyAssetPrice: String,
    var totalAssetProfit: String,
    var variationAsset: String,
    var resultType: ResultType) {

    enum class ResultType {
        POSITIVE,
        NEGATIVE,
        SAME
    }

    constructor(assetEntity: AssetEntity) : this(
        symbol = assetEntity.symbol.toString(),
        name = assetEntity.name,
        icon = assetEntity.icon.toString(),
        totalAssetInvested = "${String.format("%,.2f", assetEntity.totalInvestmentAsset.toDouble())}",
        currencyAssetPrice = "${String.format("%,.2f", assetEntity.price.toDouble())}",
        totalAssetProfit = "${String.format("%,.2f", (assetEntity.price.toDouble() - assetEntity.totalInvestmentAsset.toDouble()))}",
        "",
        resultType = ResultType.SAME
    ) {
        setupResultType(assetEntity)
        calculateVariation(assetEntity)
    }

    private fun setupResultType(assets: AssetEntity) {
        val totalInvested = assets.totalInvestmentAsset
        val totalToday = assets.price
        val difference = totalToday.toDouble() - totalInvested.toDouble()

        resultType = when {
            difference == 0.0 -> ResultType.SAME
            difference > 0.0 -> ResultType.POSITIVE
            else -> ResultType.NEGATIVE
        }
    }

    private fun calculateVariation(asset: AssetEntity) {
        val totalInvested = asset.totalInvestmentAsset.toDouble()
        val currencyPrice = asset.price.toDouble()
        var variation = ((currencyPrice - totalInvested) / totalInvested) * 100

        if (variation < 0) {
            this.variationAsset = "-${String.format("%,.0f", -variation)}%"
        } else {
            this.variationAsset = "${String.format("%,.0f", variation)}%"
        }
    }
}
