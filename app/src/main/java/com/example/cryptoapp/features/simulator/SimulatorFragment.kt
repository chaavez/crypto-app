package com.example.cryptoapp.features.simulator

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.DigitsKeyListener
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.searchAsset.SearchAssetFragment
import com.example.cryptoapp.databinding.FragmentSimulatorBinding
import com.example.cryptoapp.main.MainActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener

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
        assetValueButton()
        setupLayout()
        setupButtonAssetNavigate()
        maskedDate()
    }

    private fun setupButtonAssetNavigate() {
        binding.assetButton.setOnClickListener {
            val searchAssetFragment = SearchAssetFragment()
            (activity as? MainActivity)?.addFragment(R.id.simulator_fragment, searchAssetFragment)
        }
    }

    private fun setupLayout() {
        binding.simulatorToolbar.toolbarTextView.text = getString(R.string.simulator_title)
        binding.simulatorToolbar.toolbarImageButton.setImageResource(R.drawable.ic_settings)
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
        viewModel.saveInWallet(binding.amountTextInputEditText.text.toString(), binding.dateTextInputEditText.text.toString())
    }

    private fun setSaveButtonColorAndEnable(color: Int) {
        binding.saveInWalletButton.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        binding.saveInWalletButton.isEnabled = (color != R.color.primary_300)
    }

    private fun priceInDate() {
        viewModel.priceInDate(binding.dateTextInputEditText.text.toString(), requireContext())
    }

    private fun setDatePriceInTitle(date: String) {
        binding.priceInTittleTextView.text = date
    }

    private fun toggleTextViewsVisibility() {
        val isAllFieldsFilled = !binding.amountTextInputEditText.text.isNullOrBlank()
                && !binding.dateTextInputEditText.text.isNullOrBlank()
                && binding.dateTextInputEditText.text!!.length == 10

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

    private fun maskedDate() {
        val dateFormat = binding.dateTextInputEditText
        val listener = MaskedTextChangedListener(
            "[00]{/}[00]{/}[9900]",
            true,
            dateFormat,
            null,
            null
        )
        dateFormat.inputType = InputType.TYPE_CLASS_DATETIME
        dateFormat.keyListener = DigitsKeyListener.getInstance("0123456789 /")
        dateFormat.addTextChangedListener(listener)
        dateFormat.onFocusChangeListener = listener
    }

    private fun assetValueButton() {
        val name = arguments?.getString("name") ?: "Bitcoin"
        val symbol = arguments?.getString("symbol") ?: "BTC"
        val icon = arguments?.getString("icon") ?: "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png"
        val arrowIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        val spannable = SpannableStringBuilder("$symbol $name")
        spannable.setSpan(
            RelativeSizeSpan(1f),
            0,
            symbol?.length ?: 0,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(0.8f),
            (symbol?.length ?: 0) +1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.assetButton.text = spannable
        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(icon)
            .apply(RequestOptions.overrideOf(35,35))
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    binding.assetButton.setCompoundDrawablesRelativeWithIntrinsicBounds(resource, null, arrowIcon, null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}


