package com.example.cryptoapp.features.searchAsset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class SearchAssetViewModel(private val repository: SearchAssetRepository) : ViewModel() {
    val viewState = MutableLiveData<SearchAssetFragment.State>()
    val assets = MutableLiveData<MutableList<Asset>>()
    val filteredAssets = MutableLiveData<MutableList<Asset>>()

    fun getAssets() {
        viewState.value = SearchAssetFragment.State.LOADING
        fetchAssets()
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

    private fun fetchAssets() {
        repository.fetchAssets { assets ->
            viewState.value = SearchAssetFragment.State.CONTENT
            this.assets.value = assets
        }
    }
}