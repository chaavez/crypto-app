package com.example.cryptoapp.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorContainerBinding
import com.example.cryptoapp.features.simulator.SimulatorFragment

class FragmentSimulatorContainer : Fragment() {
    private lateinit var _binding: FragmentSimulatorContainerBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimulatorContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        start()
    }

    private fun start() {
        val simulatorFragment = SimulatorFragment()
        (activity as? MainActivity)?.addFragment(R.id.simulator_container, simulatorFragment)
    }
}