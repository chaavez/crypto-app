package com.example.cryptoapp.features.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.CryptoApp
import com.example.cryptoapp.R
import com.example.cryptoapp.database.repository.AssetEntityRepository
import com.example.cryptoapp.databinding.FragmentWalletBinding
import kotlinx.coroutines.launch

class WalletFragment : Fragment() {
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
        setupRecyclerView()
    }

    private fun setupLayout() {
        binding.walletToolbar.toolbarTextView.text = getText(R.string.wallet_title)
        binding.walletToolbar.toolbarImageButton.setImageResource(R.drawable.ic_settings)
    }

    private fun setupRecyclerView() {
        binding.myAssetsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.myAssetsRecyclerView.adapter = walletAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.assets.collect { assets ->
                walletAdapter.setAssets(assets)
            }
        }
    }
}