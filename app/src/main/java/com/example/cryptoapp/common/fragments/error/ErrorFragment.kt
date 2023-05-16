package com.example.cryptoapp.common.fragments.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentErrorBinding
import com.example.cryptoapp.features.searchAsset.SearchAssetFragmentListener
import com.example.cryptoapp.main.MainActivity

interface ErrorFragmentListener {
    fun didTryAgainClicked()
}

class ErrorFragment(private val listener: ErrorFragmentListener) : Fragment() {
    private lateinit var _binding: FragmentErrorBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //    setupLayout()
    }

//    private fun setupLayout() {
//        binding.refreshButton.setOnClickListener {
//            listener.didTryAgainClicked()
//        }
//    }
}