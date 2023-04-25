package com.example.cryptoapp.features.simulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SimulatorFragment : Fragment() {
    private lateinit var _binding: FragmentSimulatorBinding
    private lateinit var viewModel : SimulatorViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SimulatorViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.simulator_title)

        binding.amountTextInputEditText.addTextChangedListener {
            viewModel.saveInWallet(binding.amountTextInputEditText.text.toString(), binding.dateTextInputEditText.text.toString())
        }

        binding.dateTextInputEditText.addTextChangedListener {
            viewModel.saveInWallet(binding.amountTextInputEditText.text.toString(), binding.dateTextInputEditText.text.toString())
        }

        viewModel.saveButtonColor.observe(viewLifecycleOwner) { color ->
            binding.saveInWalletButton.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
            binding.saveInWalletButton.isEnabled = (color != R.color.primary_300)
        }
    }
}