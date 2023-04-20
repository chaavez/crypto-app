package com.example.cryptoapp.common.fragments.mostValued

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class MostValuedViewModel(private val repository: MostValuedRepository) : ViewModel() {
    val assets = MutableLiveData<MutableList<Asset>>()
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 10000L
    private val runnable = object : Runnable {

        override fun run() {
            getAssets()
            handler.postDelayed(this, delay)
        }
    }

    fun startPolling() {
        handler.postDelayed(runnable, delay)
    }

    fun stopPolling() {
        handler.removeCallbacks(runnable)
    }

    fun getAssets() {
        repository.fetchAssets { assets ->
            this.assets.value = assets
        }
    }
}