package com.example.cryptoapp.features.wallet

import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.services.network.apis.assets.AssetResponse
import com.example.cryptoapp.services.network.apis.assets.AssetsRequest
import com.example.cryptoapp.services.network.httpProvider.APIError
import com.example.cryptoapp.services.network.httpProvider.RetrofitProvider

class WalletRepository {
    fun fetchAsset(symbol: String, date: String, callback: (Asset) -> Unit, callbackError: (APIError) -> Unit) {
        val assetsRequest = AssetsRequest(RetrofitProvider())

        assetsRequest.getAsset(symbol, date) { assetResponse, apiError ->
            assetResponse?.let {
                callback(converter(it))
            }
            apiError?.let {
                callbackError(it)
            }
        }
    }

    private fun converter(asset: AssetResponse) : Asset {
        return Asset(asset.symbol, asset.name, asset.icon, asset.price, asset.variation)
    }
}