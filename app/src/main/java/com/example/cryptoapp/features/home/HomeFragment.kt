package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.highlights.HighlightsFragment
import com.example.cryptoapp.common.fragments.mostValued.MostValuedFragment
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.models.HighlightsAdapter
import com.example.cryptoapp.models.MostValuedAdapter

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding!!

    private var mostValuedAdapter: MostValuedAdapter = MostValuedAdapter()
    private var highlightsAdapter: HighlightsAdapter = HighlightsAdapter()

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this, HomeViewModelFactory(HomeRepository())).get(HomeViewModel::class.java)

        setupLayout()
        initMostValuedFragment()
        initHighlightsFragment()
        observeViewModel()
        getAssets()

        return root
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.home_title)
    }

    private fun initMostValuedFragment() {
        val mostValuedFragment = MostValuedFragment.newInstance(mostValuedAdapter)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_most_valued, mostValuedFragment)
        fragmentTransaction.commit()
    }

    private fun initHighlightsFragment() {
        val highlightsFragment = HighlightsFragment.newInstance(highlightsAdapter)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_highlights, highlightsFragment)
        fragmentTransaction.commit()
    }

    private fun observeViewModel() {
        viewModel.assets.observe(viewLifecycleOwner, Observer { assets ->
            this.updateMostValued(assets)
            this.updateHighlights(assets)
        })
    }

    private fun getAssets() {
        viewModel.getAssets()
    }

    private fun updateMostValued(assets: List<Asset>) {
        mostValuedAdapter.setAssets(assets)
        mostValuedAdapter.notifyDataSetChanged()
    }

    private fun updateHighlights(assets: List<Asset>) {
        highlightsAdapter.setAssets(assets)
        highlightsAdapter.notifyDataSetChanged()
    }
}