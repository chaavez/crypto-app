package com.example.cryptoapp.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    val assets = MutableLiveData<MutableList<Asset>>()

    fun getAssets() {
        repository.fetchAssets { assets ->
            this.assets.value = assets
        }
    }
}