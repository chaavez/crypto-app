package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.models.MostValuedAdapter


class MostValuedFragment : Fragment() {
    private lateinit var mostValuedAdapter: MostValuedAdapter

    companion object {
        fun newInstance(mostValuedAdapter: MostValuedAdapter): MostValuedFragment {
            val fragment = MostValuedFragment()
            fragment.mostValuedAdapter = mostValuedAdapter
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_most_valued, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.most_valued_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mostValuedAdapter

        return view
    }
}
