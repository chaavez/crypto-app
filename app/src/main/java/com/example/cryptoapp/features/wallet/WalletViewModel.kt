package com.example.cryptoapp.features.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.database.repository.AssetEntityRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AssetEntityRepository) : ViewModel() {
    val viewState =  MutableLiveData<WalletFragment.State>()

    private val _assets: MutableLiveData<List<AssetEntity>> = MutableLiveData()
    val assets: LiveData<List<AssetEntity>> = _assets

    fun loadAssets() {
        viewModelScope.launch {
            val assetsList = repository.getAllAssets().first()
            if(assetsList.isEmpty()) {
                viewState.value = WalletFragment.State.EMPTY
            } else {
                _assets.postValue(assetsList)
                viewState.value = WalletFragment.State.CONTENT
            }
        }
    }
}