package com.example.cryptoapp.features.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.CryptoApp
import com.example.cryptoapp.R
import com.example.cryptoapp.database.repository.AssetEntityRepository
import com.example.cryptoapp.databinding.FragmentErrorBinding
import com.example.cryptoapp.databinding.FragmentErrorWalletBinding
import com.example.cryptoapp.databinding.FragmentWalletBinding
import com.example.cryptoapp.main.MainActivity
import kotlinx.coroutines.launch

class WalletFragment : Fragment() {
    enum class State {
        EMPTY,
        CONTENT,
        ERROR
    }

    private lateinit var _binding: FragmentWalletBinding
    private lateinit var viewModel: WalletViewModel
    private val binding get() = _binding
    private val walletAdapter = WalletAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val assetDao = CryptoApp.getAssetDao()
        viewModel = ViewModelProvider(this, WalletViewModelFactory(AssetEntityRepository(assetDao)))[WalletViewModel::class.java]
        setupLayout()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAssets()
    }

    private fun setupLayout() {
        binding.walletToolbar.toolbarTextView.text = getText(R.string.wallet_title)
        binding.walletToolbar.toolbarImageButton.setImageResource(R.drawable.ic_settings)
    }

    private fun setupRecyclerView(formattedAsset: WalletFormatter) {
        binding.myAssetsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.myAssetsRecyclerView.adapter = walletAdapter
        walletAdapter.setAssets(formattedAsset.walletAssets)
    }

    private fun observeViewModel() {
        viewModel.assetPricesFormatter.observe(viewLifecycleOwner) { formatter ->
            fillAssetsPrice(formatter)
            setupRecyclerView(formatter)
        }
    }

    private fun setupState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when(state) {
                State.EMPTY -> {
                    setupInvestedAssets(View.INVISIBLE)
                    val emptyWalletFragment = EmptyWalletFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_wallet_state, emptyWalletFragment)
                    binding.fragmentWalletState.visibility = View.VISIBLE
                    binding.myAssetsRecyclerView.visibility = View.INVISIBLE
                }
                State.CONTENT -> {
                    binding.fragmentWalletState.visibility = View.INVISIBLE
                    binding.myAssetsRecyclerView.visibility = View.VISIBLE
                    setupInvestedAssets(View.VISIBLE)
                }
                State.ERROR -> {
                    val errorFragmentWallet = ErrorWalletFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_wallet_state, errorFragmentWallet)
                    binding.fragmentWalletState.visibility = View.VISIBLE
                    binding.myAssetsRecyclerView.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun setupInvestedAssets(visibility: Int) {
        binding.totalInvestedTittle.visibility = visibility
        binding.totalInvested.visibility = visibility
        binding.totalTodayTittle.visibility = visibility
        binding.totalToday.visibility = visibility
        binding.totalProfitTittle.visibility = visibility
        binding.totalProfit.visibility = visibility
        binding.myAssetsTextView.visibility = visibility
        binding.variationTotalProfit.visibility = visibility
    }

    private fun fillAssetsPrice(formatter: WalletFormatter) {
        binding.totalInvested.text = formatter.totalInvested
        binding.totalToday.text = formatter.totalToday
        binding.totalProfit.text = formatter.totalProfit
        binding.variationTotalProfit.text = formatter.variationPercentage

       when (formatter.resultType) {
           WalletFormatter.ResultType.SAME -> {
               binding.totalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.tertiary_100))
               binding.variationTotalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_100))
           }
           WalletFormatter.ResultType.POSITIVE -> {
               binding.totalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_100))
               binding.variationTotalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_100))
           }
           WalletFormatter.ResultType.NEGATIVE -> {
               binding.totalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_100))
               binding.variationTotalProfit.setTextColor(ContextCompat.getColor(requireContext(), R.color.secondary_100))
           }
       }
    }
}