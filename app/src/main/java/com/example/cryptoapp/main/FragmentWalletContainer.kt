package com.example.cryptoapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoapp.databinding.FragmentWalletContainerBinding

class FragmentWalletContainer : Fragment() {
    private lateinit var _binding: FragmentWalletContainerBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletContainerBinding.inflate(inflater, container, false)
        return binding.root
    }
}