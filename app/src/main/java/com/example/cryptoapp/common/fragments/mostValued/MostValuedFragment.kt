package com.example.cryptoapp.common.fragments.mostValued

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.models.MostValuedAdapter


class MostValuedFragment : Fragment() {
    private val mostValuedAdapter = MostValuedAdapter()
    private lateinit var viewModel: MostValuedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_most_valued, container, false)
        viewModel = ViewModelProvider(requireParentFragment(), MostValuedViewModelFactory(MostValuedRepository())).get(MostValuedViewModel::class.java)
        val recyclerView = view.findViewById<RecyclerView>(R.id.most_valued_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mostValuedAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            mostValuedAdapter.setAssets(newData)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }
}
