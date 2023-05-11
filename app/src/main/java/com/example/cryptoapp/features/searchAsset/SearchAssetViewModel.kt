package com.example.cryptoapp.features.searchAsset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class SearchAssetViewModel(private val repository: SearchAssetRepository) : ViewModel() {
    val assets = MutableLiveData<MutableList<Asset>>()
    val filteredAssets = MutableLiveData<MutableList<Asset>>()

    fun getAssets() {
        repository.fetchAssets { assets ->
            this.assets.value = assets
            filteredAssets.value = assets
        }
    }

    fun filterAssets(query: String) {
        if(query.isEmpty()) {
            filteredAssets.value = assets.value
        } else {
            filteredAssets.value = assets.value?.filter {
                it.name.contains(query.lowercase(), true)
            }?.toMutableList()
        }
    }
}