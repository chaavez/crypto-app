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
import com.example.cryptoapp.common.fragments.Loading.LoadingFragment
import com.example.cryptoapp.features.searchAsset.SearchAssetFragment
import com.example.cryptoapp.features.searchAsset.SearchAssetFragmentListener
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.FragmentSimulatorBinding
import com.example.cryptoapp.main.MainActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener

class SimulatorFragment : Fragment(), SearchAssetFragmentListener {
    enum class State {
        STAND_BY,
        TO_SAVE,
        LOADING,
        ERROR
    }

    private lateinit var _binding: FragmentSimulatorBinding
    private lateinit var viewModel: SimulatorViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSimulatorBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity(), SimulatorViewModelFactory(
            SimulatorRepository()
        ))[SimulatorViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        maskedDate()
        observeViewModel()
        setupState()
        viewModel.loadFirstAsset()
    }

    private fun setupLayout() {
        binding.simulatorToolbar.toolbarTextView.text = getString(R.string.simulator_title)
        binding.simulatorToolbar.toolbarImageButton.setImageResource(R.drawable.ic_settings)

        binding.assetButton.setOnClickListener {
            val searchAssetFragment = SearchAssetFragment(this)
            (activity as? MainActivity)?.addFragment(R.id.simulator_container, searchAssetFragment)
        }

        binding.amountTextInputEditText.addTextChangedListener { text ->
            viewModel.updateAmount(text.toString(), requireContext())
        }

        binding.dateTextInputEditText.addTextChangedListener { text ->
            viewModel.updateDate(text.toString(), requireContext())
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

    private fun observeViewModel() {
        viewModel.asset.observe(viewLifecycleOwner) { asset ->
            fillAssetButton(asset)
        }

        viewModel.onAmountError.observe(viewLifecycleOwner) { error ->
            binding.amountOutlinedTextField.error = error
        }

        viewModel.onDateError.observe(viewLifecycleOwner) { error ->
            binding.dateOutlinedTextField.error = error
        }

        viewModel.assetPricesFormatter.observe(viewLifecycleOwner) { formatter ->
            fillAssetPrices(formatter)
        }
    }

    private fun setupState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.STAND_BY -> {
                    setupAssetPrices(View.INVISIBLE)
                    binding.saveInWalletButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary_300))
                    binding.saveInWalletButton.isEnabled = false
                    binding.fragmentSimulatorState.visibility = View.INVISIBLE
                }
                State.TO_SAVE -> {
                    setupAssetPrices(View.VISIBLE)
                    binding.saveInWalletButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary_200))
                    binding.saveInWalletButton.isEnabled = true
                    binding.fragmentSimulatorState.visibility = View.INVISIBLE
                }
                State.LOADING -> {
                    val loadingFragment = LoadingFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_simulator_state, loadingFragment)
                    setupAssetPrices(View.INVISIBLE)
                    binding.fragmentSimulatorState.visibility = View.VISIBLE
                }
                State.ERROR -> {
                    val invalidDateError = InvalidDateErrorFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_simulator_state, invalidDateError)
                    setupAssetPrices(View.INVISIBLE)
                    binding.fragmentSimulatorState.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupAssetPrices(visibility: Int) {
        binding.priceInTittleTextView.visibility = visibility
        binding.priceInTextView.visibility = visibility
        binding.currentPriceTittleTextView.visibility = visibility
        binding.currentPriceTextView.visibility = visibility
        binding.resultPriceTittleTextView.visibility = visibility
        binding.resultPriceTextView.visibility = visibility
        binding.resultVariationTextView.visibility = visibility
    }

    private fun fillAssetButton(asset: Asset) {
        val arrowIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .override(45,45)

        val spannable = SpannableStringBuilder("${asset.symbol} ${asset.name}")
        spannable.setSpan(
            RelativeSizeSpan(1f),
            0,
            asset.symbol?.length ?: 0,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            RelativeSizeSpan(0.8f),
            (asset.symbol?.length ?: 0) +1,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.assetButton.text = spannable
        Glide.with(requireContext())
            .applyDefaultRequestOptions(requestOptions)
            .load(asset.icon)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    binding.assetButton.setCompoundDrawablesRelativeWithIntrinsicBounds(resource, null, arrowIcon, null)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun fillAssetPrices(formatter: AssetPricesFormatter) {
        binding.priceInTittleTextView.text = formatter.oldAssetDate
        binding.priceInTextView.text = formatter.oldAssetPrice
        binding.currentPriceTextView.text = formatter.currentAssetPrice
        binding.resultPriceTextView.text = formatter.resultPrice
        binding.resultVariationTextView.text = formatter.resultVariation

        when (formatter.resultType) {
            AssetPricesFormatter.ResultType.POSITIVE -> {
                binding.resultPriceTittleTextView.text = getString(R.string.simulator_would_invoice)
                binding.resultPriceTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_100))
                binding.resultVariationTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_100))
            }
            AssetPricesFormatter.ResultType.NEGATIVE -> {
                binding.resultPriceTittleTextView.text = getString(R.string.simulator_would_lose)
                binding.resultPriceTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_100))
                binding.resultVariationTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_100))

            }
            AssetPricesFormatter.ResultType.SAME -> {
                binding.resultPriceTittleTextView.text = getString(R.string.simulator_would_invoice)
                binding.resultPriceTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_100))
                binding.resultVariationTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_100))
            }
        }
    }

    override fun didAssetClicked(asset: Asset) {
        viewModel.updateAsset(asset)
    }
}