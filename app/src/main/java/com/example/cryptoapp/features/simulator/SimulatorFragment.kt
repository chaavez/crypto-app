package com.example.cryptoapp.features.simulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SimulatorFragment : Fragment() {
    private var _binding: FragmentSimulatorBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupLayout()

        return root
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.simulator_title)
    }
}