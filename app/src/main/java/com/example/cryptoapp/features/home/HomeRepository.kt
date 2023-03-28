package com.example.cryptoapp.features.home

import androidx.lifecycle.MutableLiveData
import com.example.cryptoapp.models.Asset
import com.example.cryptoapp.services.network.NetworkTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class HomeRepository {
    fun fetchAssets(callback: (MutableList<Asset>) -> Unit) {
        val network = NetworkTask()
        GlobalScope.launch {
            val result = network.makeRequest("https://crypto-could-i-have-won-production.up.railway.app/assets")
            val jsonArray = JSONArray(result)
            val assets = mutableListOf<Asset>()
            for (i in 0 until jsonArray.length()) {
                val temporaryAsset = jsonArray.getJSONObject(i)
                val asset = Asset(
                    temporaryAsset.getString("symbol"),
                    temporaryAsset.getString("name"),
                    temporaryAsset.getString("icon"),
                    temporaryAsset.getDouble("price"),
                    temporaryAsset.getDouble("variation"),
                )
                assets.add(asset)
            }
            callback(assets)
        }
    }
}