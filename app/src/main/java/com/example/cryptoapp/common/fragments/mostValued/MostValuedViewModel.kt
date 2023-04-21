package com.example.cryptoapp.common.fragments.mostValued

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class MostValuedViewModel(private val repository: MostValuedRepository) : ViewModel() {
    val assets = MutableLiveData<MutableList<Asset>>()
    private var startedPolling = false
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 10000L
    private val runnable = object : Runnable {

        override fun run() {
            getAssets()
            handler.postDelayed(this, delay)
        }
    }

    private fun startPolling() {
        if(!startedPolling) {
            handler.postDelayed(runnable, delay)
            startedPolling = true
        }
    }

    fun stopPolling() {
        handler.removeCallbacks(runnable)
    }

    fun getAssets() {
        repository.fetchAssets { assets ->
            this.assets.value = assets
            startPolling()
        }
    }
}