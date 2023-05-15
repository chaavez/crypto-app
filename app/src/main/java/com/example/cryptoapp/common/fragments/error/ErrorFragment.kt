package com.example.cryptoapp.common.fragments.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoapp.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {
    private lateinit var _binding: FragmentErrorBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }
}