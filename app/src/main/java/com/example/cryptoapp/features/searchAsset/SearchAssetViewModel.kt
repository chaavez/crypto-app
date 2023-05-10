package com.example.cryptoapp.features.searchAsset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.common.models.Mock

class SearchAssetViewModel : ViewModel() {
    private var assets = mutableListOf<Asset>()
    val filteredAssets = MutableLiveData<MutableList<Asset>>()

    fun getAssets() {
        assets = Mock.mockData()
        filteredAssets.value = assets
    }

    fun filterAssets(query: String) {
        if(query.isEmpty()) {
            filteredAssets.value = assets
        } else {
            filteredAssets.value = assets.filter {
                it.name.contains(query.lowercase(), true)
            }.toMutableList() 
        }
    }
}