package com.example.cryptoapp.common.fragments.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptoapp.databinding.FragmentErrorAssetBinding

class ErrorAssetFragment : Fragment() {
    private lateinit var _binding: FragmentErrorAssetBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentErrorAssetBinding.inflate(inflater, container, false)
        return binding.root
    }
}