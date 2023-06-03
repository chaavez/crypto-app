package com.example.cryptoapp.features.simulator

import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.services.network.apis.assets.AssetResponse
import com.example.cryptoapp.services.network.apis.assets.AssetsRequest
import com.example.cryptoapp.services.network.httpProvider.RetrofitProvider

class SimulatorRepository {
    fun fetchAsset(symbol: String, date: String, callback: (Asset) -> Unit) {
        val assetsRequest = AssetsRequest(RetrofitProvider())

        assetsRequest.getAsset(symbol, date) { assetResponse, apiError ->
            assetResponse?.let {
                callback(converter(it))
            }
        }
    }

    private fun converter(asset: AssetResponse) : Asset {
        return Asset(asset.symbol, asset.name, asset.icon, asset.price, asset.variation)
    }
}