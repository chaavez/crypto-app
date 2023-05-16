package com.example.cryptoapp.common.fragments.highlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.Loading.LoadingFragment
import com.example.cryptoapp.common.fragments.mostValued.MostValuedFragment
import com.example.cryptoapp.databinding.FragmentHighlightsBinding
import com.example.cryptoapp.main.MainActivity

class HighlightsFragment : Fragment() {

    enum class State {
        CONTENT,
        ERROR,
        LOADING
    }

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
        setupRecyclerView()
        setupState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAssets()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
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

    private fun setupState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.CONTENT -> {

                }
                State.LOADING -> {
                    val loadingFragment = LoadingFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_highlights, loadingFragment)
                }
                State.ERROR -> {

                }
            }
        }
    }
}