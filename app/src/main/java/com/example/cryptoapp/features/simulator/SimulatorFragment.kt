package com.example.cryptoapp.features.simulator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SimulatorFragment : Fragment() {
    private lateinit var _binding: FragmentSimulatorBinding
    private lateinit var viewModel: SimulatorViewModel
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
        setupButtonAssetNavigate()
    }

    private fun setupButtonAssetNavigate() {
        binding.assetButton.setOnClickListener {
        }
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.simulator_title)
        binding.amountTextInputEditText.addTextChangedListener {
            saveAmountAndDate()
            toggleTextViewsVisibility()
        }

        binding.dateTextInputEditText.addTextChangedListener {
            saveAmountAndDate()
            toggleTextViewsVisibility()
        }

        viewModel.saveButtonColor.observe(viewLifecycleOwner) { color ->
            setSaveButtonColorAndEnable(color)
            toggleTextViewsVisibility()
        }

        binding.dateTextInputEditText.addTextChangedListener {
            priceInDate()
            toggleTextViewsVisibility()
        }

        viewModel.datePriceInTittle.observe(viewLifecycleOwner) { date ->
            setDatePriceInTitle(date)
            toggleTextViewsVisibility()
        }
    }

    private fun saveAmountAndDate() {
        viewModel.saveInWallet(
            binding.amountTextInputEditText.text.toString(),
            binding.dateTextInputEditText.text.toString()
        )
    }

    private fun setSaveButtonColorAndEnable(color: Int) {
        binding.saveInWalletButton.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
        binding.saveInWalletButton.isEnabled = (color != R.color.primary_300)
    }

    private fun priceInDate() {
        viewModel.priceInDate(binding.dateTextInputEditText.text.toString(), requireContext())
    }

    private fun setDatePriceInTitle(date: String) {
        binding.priceInTittleTextView.text = date
    }

    private fun toggleTextViewsVisibility() {
        val isAllFieldsFilled = !binding.amountTextInputEditText.text.isNullOrBlank() && !binding.dateTextInputEditText.text.isNullOrBlank()

        if (isAllFieldsFilled) {
            binding.priceInTittleTextView.visibility = View.VISIBLE
            binding.priceInTextView.visibility = View.VISIBLE
            binding.currentPriceTittleTextView.visibility = View.VISIBLE
            binding.currentPriceTextView.visibility = View.VISIBLE
            binding.resultPriceTittleTextView.visibility = View.VISIBLE
            binding.resultPriceTextView.visibility = View.VISIBLE
        } else {
            binding.priceInTittleTextView.visibility = View.INVISIBLE
            binding.priceInTextView.visibility = View.INVISIBLE
            binding.currentPriceTittleTextView.visibility = View.INVISIBLE
            binding.currentPriceTextView.visibility = View.INVISIBLE
            binding.resultPriceTittleTextView.visibility = View.INVISIBLE
            binding.resultPriceTextView.visibility = View.INVISIBLE
        }
    }
}