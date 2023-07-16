package com.example.cryptoapp.features.searchAsset

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.loading.LoadingFragment
import com.example.cryptoapp.common.fragments.error.ErrorFragment
import com.example.cryptoapp.common.fragments.error.ErrorFragmentListener
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.FragmentSearchAssetBinding
import com.example.cryptoapp.main.MainActivity

interface SearchAssetFragmentListener {
    fun didAssetClicked(asset: Asset)
}

class SearchAssetFragment(private val listener: SearchAssetFragmentListener) : Fragment(),
    SearchAssetAdapterListener, ErrorFragmentListener {
    enum class State {
        CONTENT,
        ERROR,
        LOADING
    }

    private val searchAssetAdapter = SearchAssetAdapter(listener = this)
    private lateinit var viewModel: SearchAssetViewModel
    private lateinit var _binding: FragmentSearchAssetBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAssetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, SearchAssetViewModelFactory(SearchAssetRepository())).get(SearchAssetViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupState()
        setupRecyclerView()
        observeAssetEditText()
        setupSearchView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAssets()
    }

    private fun setupLayout() {
        binding.searchAssetToolbar.toolbarTextView.text = getString(R.string.search_asset_tittle)
        binding.searchAssetToolbar.toolbarImageButton.setImageResource(R.drawable.ic_arrow_back)
        binding.searchAssetToolbar.toolbarImageButton.setOnClickListener {
            (activity as? MainActivity)?.pop()
        }
    }

    private fun setupRecyclerView() {
        binding.searchAssetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchAssetRecyclerView.adapter = searchAssetAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            searchAssetAdapter.setAssets(newData)
            binding.searchAssetRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupSearchView() {
        viewModel.filteredAssets.observe(viewLifecycleOwner) { assets ->
            searchAssetAdapter.setAssets(assets)
        }
    }

    private fun observeAssetEditText() {
        binding.assetSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.filterAssets(query.toString())
                return true
            }
        })
    }

    private fun setupState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.CONTENT -> {
                    binding.fragmentSearchAssetState.visibility = View.INVISIBLE
                    binding.searchAssetRecyclerView.visibility = View.VISIBLE
                }
                State.LOADING -> {
                    val loadingFragment = LoadingFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_search_asset_state, loadingFragment)

                    binding.fragmentSearchAssetState.visibility = View.VISIBLE
                    binding.searchAssetRecyclerView.visibility = View.INVISIBLE
                }
                State.ERROR -> {
                    val errorFragment = ErrorFragment(this)
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_search_asset_state, errorFragment)

                    binding.fragmentSearchAssetState.visibility = View.VISIBLE
                    binding.searchAssetRecyclerView.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun didAssetClicked(asset: Asset) {
        listener.didAssetClicked(asset)
        (activity as? MainActivity)?.pop()
    }

    override fun didTryAgainClicked() {
        viewModel.getAssets()
    }
}