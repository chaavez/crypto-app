package com.example.cryptoapp.common.fragments.mostValued

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class MostValuedViewModel(private val repository: MostValuedRepository) : ViewModel() {
    val viewState = MutableLiveData<MostValuedFragment.State>()
    val assets = MutableLiveData<MutableList<Asset>>()

    private var startedPolling = false
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 10000L
    private val runnable = object : Runnable {

        override fun run() {
            fetchAssets()
            handler.postDelayed(this, delay)
        }
    }

    fun stopPolling() {
        handler.removeCallbacks(runnable)
    }

    fun getAssets() {
        viewState.value = MostValuedFragment.State.LOADING
        fetchAssets()
    }

    private fun startPolling() {
        if(!startedPolling) {
            handler.postDelayed(runnable, delay)
            startedPolling = true
        }
    }

    private fun fetchAssets() {
        repository.fetchAssets { assets ->
            viewState.value = MostValuedFragment.State.CONTENT
            this.assets.value = assets
            startPolling()
        }
    }
}