package com.example.cryptoapp.common.fragments.Loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.FragmentLoadingBinding

class LoadingFragment : Fragment() {
    private lateinit var _binding: FragmentLoadingBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }
}