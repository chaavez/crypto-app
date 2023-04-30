package com.example.cryptoapp.features.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentWalletBinding

class WalletFragment : Fragment() {
    private var _binding: FragmentWalletBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupLayout()
        return root
    }

    private fun setupLayout() {
        binding.walletToolbar.toolbarTextView.text = getText(R.string.wallet_title)
    }
}