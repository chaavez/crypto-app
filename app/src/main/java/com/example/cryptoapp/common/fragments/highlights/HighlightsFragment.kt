package com.example.cryptoapp.common.fragments.highlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptoapp.databinding.FragmentHighlightsBinding

class HighlightsFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private val highlightsAdapter = HighlightsAdapter()
    private lateinit var viewModel: HighlightsViewModel
    private lateinit var _binding: FragmentHighlightsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHighlightsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment(), HighlightsViewModelFactory(HighlightsRepository())).get(HighlightsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarIndicator()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAssets()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }

    private fun progressBarIndicator() {
        progressBar = binding.progressCircular
        progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.highlightsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        binding.highlightsRecyclerView.adapter = highlightsAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            highlightsAdapter.setAssets(newData)
            binding.highlightsRecyclerView.adapter?.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }
    }
}