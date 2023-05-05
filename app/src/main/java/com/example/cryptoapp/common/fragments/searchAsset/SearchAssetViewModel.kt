package com.example.cryptoapp.common.fragments.searchAsset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Mock
import com.example.cryptoapp.common.models.SearchAsset

class SearchAssetViewModel : ViewModel() {
    private var assets = mutableListOf<SearchAsset>()
    val filteredAssets = MutableLiveData<MutableList<SearchAsset>>()

    fun getAssets() {
        assets = Mock.mockData()
        filteredAssets.value = assets
    }

    fun filterAssets(query: String) {
        if(query.isEmpty()) {
            filteredAssets.value = assets
        } else {
            filteredAssets.value = assets.filter { it.name.contains(query.lowercase(), true) }.toMutableList()
        }
    }
}