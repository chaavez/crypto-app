package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.models.HighlightsAdapter

class HighlightsFragment : Fragment() {
    private lateinit var highlightsAdapter: HighlightsAdapter

    companion object {
        fun newInstance(highlightsAdapter: HighlightsAdapter): HighlightsFragment {
            val fragment = HighlightsFragment()
            fragment.highlightsAdapter = highlightsAdapter
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isInitialized()
        val view = inflater.inflate(R.layout.fragment_highlights, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.highlights_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = highlightsAdapter

        return view
    }

    fun isInitialized() {
        if(!::highlightsAdapter.isInitialized) {
            highlightsAdapter = HighlightsAdapter()
        }
    }
}