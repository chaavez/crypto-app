package com.example.cryptoapp.common.fragments.mostValued

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.databinding.FragmentMostValuedBinding


class MostValuedFragment : Fragment() {
    private val mostValuedAdapter = MostValuedAdapter()
    private lateinit var viewModel: MostValuedViewModel
    private lateinit var _binding: FragmentMostValuedBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostValuedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment(), MostValuedViewModelFactory(MostValuedRepository())).get(MostValuedViewModel::class.java)
        setupRecyclerView()
        return binding.root
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
        binding.mostValuedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.mostValuedRecyclerView.adapter = mostValuedAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            mostValuedAdapter.setAssets(newData)
            binding.mostValuedRecyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
