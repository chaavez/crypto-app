package com.example.cryptoapp.common.fragments.mostValued

import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.services.network.apis.assets.AssetResponse
import com.example.cryptoapp.services.network.apis.assets.AssetsRequest
import com.example.cryptoapp.services.network.httpProvider.RetrofitProvider

class MostValuedRepository {
    fun fetchAssets(callback: (MutableList<Asset>) -> Unit) {
        val assetsRequest = AssetsRequest(RetrofitProvider())

        assetsRequest.get { assetsResponse, apiError ->
            callback(assetsResponse?.let { converter(it) } as MutableList<Asset>)
        }
    }

    private fun converter(assets: List<AssetResponse>) : List<Asset> {
        return assets.map { it
            Asset(it.symbol, it.name, it.icon, it.price, it.variation)
        }
    }
}