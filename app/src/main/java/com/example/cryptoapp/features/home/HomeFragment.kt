package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.highlights.HighlightsFragment
import com.example.cryptoapp.common.fragments.mostValued.MostValuedFragment
import com.example.cryptoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val highlightsFragment = HighlightsFragment()
        val mostValuedFragment = MostValuedFragment()
        setupLayout()
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_highlights, highlightsFragment)
            .replace(R.id.fragment_container_most_valued, mostValuedFragment)
            .commit()
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.home_title)
    }
}