package com.example.cryptoapp.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.features.home.MostValuedFragment
import com.example.cryptoapp.models.MostValuedAdapter
import kotlinx.coroutines.*


class LoadingIndicatorFragment : Fragment() {
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading_indicator, container, false)
        progressBar = view.findViewById(R.id.loading_indicator)
        return view
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}