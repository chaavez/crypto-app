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
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startProgressBarIndicator()
    }

    private fun startProgressBarIndicator() {
        binding.progressCircular.visibility = View.VISIBLE
    }

}