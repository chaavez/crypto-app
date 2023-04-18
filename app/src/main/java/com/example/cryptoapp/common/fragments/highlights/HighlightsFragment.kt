package com.example.cryptoapp.common.fragments.highlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.features.home.HomeRepository
import com.example.cryptoapp.features.home.HomeViewModel
import com.example.cryptoapp.features.home.HomeViewModelFactory
import com.example.cryptoapp.models.HighlightsAdapter

class HighlightsFragment : Fragment() {
    private val highlightsAdapter = HighlightsAdapter()
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_highlights, container, false)
        viewModel = ViewModelProvider(requireParentFragment(), HomeViewModelFactory(HomeRepository())).get(HomeViewModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.highlights_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = highlightsAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            highlightsAdapter.setAssets(newData)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        return view
    }
}