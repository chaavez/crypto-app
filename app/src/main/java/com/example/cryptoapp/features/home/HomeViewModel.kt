package com.example.cryptoapp.features.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.models.Asset

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    val assets = MutableLiveData<MutableList<Asset>>()

    fun getAssets() {
        repository.fetchAssets { assets ->
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                this.assets.value = assets
            }
        }
    }
}