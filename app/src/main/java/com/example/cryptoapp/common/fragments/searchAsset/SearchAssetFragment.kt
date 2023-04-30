package com.example.cryptoapp.common.fragments.searchAsset

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentSearchAssetBinding
import com.example.cryptoapp.databinding.FragmentSimulatorBinding

class SearchAssetFragment : Fragment() {
    private val searchAssetAdapter = SearchAssetAdapter()
    private lateinit var binding: FragmentSearchAssetBinding
    private lateinit var viewModel: SearchAssetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_asset, container, false)
        binding = FragmentSearchAssetBinding.inflate(inflater, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_asset_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = searchAssetAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchAssetViewModel::class.java)
        setupLayout()
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.search_asset_tittle)
    }
}