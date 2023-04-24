package com.example.cryptoapp.features.simulator

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SimulatorFragment : Fragment() {
    private lateinit var _binding: FragmentSimulatorBinding
    private val viewModel: SimulatorViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.simulator_title)
        val color = ContextCompat.getColor(requireContext(), R.color.secondary_200)
        binding.saveInWalletButton.setBackgroundColor(color)
    }
}