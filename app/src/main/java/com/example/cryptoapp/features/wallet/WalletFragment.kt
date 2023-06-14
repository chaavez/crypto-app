package com.example.cryptoapp.features.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {
    private lateinit var _binding: FragmentWalletBinding
    private val binding get() = _binding
    private val walletAdapter = WalletAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupLayout()
        return root
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
        binding.myAssetsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.myAssetsRecyclerView.adapter = walletAdapter
    }
}