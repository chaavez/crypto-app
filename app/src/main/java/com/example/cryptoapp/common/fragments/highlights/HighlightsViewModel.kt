package com.example.cryptoapp.common.fragments.highlights

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.common.models.Asset

class HighlightsViewModel(private val repository: HighlightsRepository) : ViewModel() {
    val viewState = MutableLiveData<HighlightsFragment.State>()
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

    fun getAssets() {
        viewState.value = HighlightsFragment.State.LOADING
        fetchAssets()
    }

    fun stopPolling() {
        handler.removeCallbacks(runnable)
    }

    private fun startPolling() {
        if(!startedPolling) {
            handler.postDelayed(runnable, delay)
            startedPolling = true
        }
    }

    private fun fetchAssets() {
        repository.fetchAssets { assets ->
            viewState.value = HighlightsFragment.State.CONTENT
            this.assets.value = assets
            startPolling()
        }
    }
}




















