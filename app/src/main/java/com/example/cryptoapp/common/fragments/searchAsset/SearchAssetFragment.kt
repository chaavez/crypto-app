package com.example.cryptoapp.common.fragments.searchAsset

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Mock
import com.example.cryptoapp.databinding.FragmentSearchAssetBinding

class SearchAssetFragment : Fragment() {
    private val searchAssetAdapter = SearchAssetAdapter()
    private lateinit var viewModel: SearchAssetViewModel
    private lateinit var _binding: FragmentSearchAssetBinding
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAssetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchAssetViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupRecyclerView()
        mocks()
        observeAssetEditText()
    }

    private fun setupLayout() {
        binding.searchAssetToolbar.toolbarTextView.text = getString(R.string.search_asset_tittle)
        binding.searchAssetToolbar.toolbarImageButton.setImageResource(R.drawable.ic_back_arrow)
        binding.searchAssetToolbar.toolbarImageButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.searchAssetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchAssetRecyclerView.adapter = searchAssetAdapter
    }

    private fun observeAssetEditText() {
        binding.searchAssetEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                print("")
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchAssetAdapter.setAssets(Mock.mockData2())
            }

            override fun afterTextChanged(p0: Editable?) {
                print("")
            }
        })
    }

    private fun mocks() {
        val mock = Mock.mockData()
        searchAssetAdapter.setAssets(mock)
    }
}