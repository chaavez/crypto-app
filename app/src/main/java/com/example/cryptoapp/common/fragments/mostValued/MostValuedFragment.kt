package com.example.cryptoapp.common.fragments.mostValued

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.common.fragments.Loading.LoadingFragment
import com.example.cryptoapp.common.fragments.error.ErrorFragment
import com.example.cryptoapp.common.fragments.error.ErrorFragmentListener
import com.example.cryptoapp.databinding.FragmentMostValuedBinding
import com.example.cryptoapp.main.MainActivity


class MostValuedFragment : Fragment(), ErrorFragmentListener {
    enum class State {
        CONTENT,
        ERROR,
        LOADING
    }

    private val mostValuedAdapter = MostValuedAdapter()
    private lateinit var viewModel: MostValuedViewModel
    private lateinit var _binding: FragmentMostValuedBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostValuedBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireParentFragment(), MostValuedViewModelFactory(MostValuedRepository()))[MostValuedViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupState()
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

    private fun setupRecyclerView() {
        binding.mostValuedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.mostValuedRecyclerView.adapter = mostValuedAdapter
        viewModel.assets.observe(viewLifecycleOwner) { newData ->
            mostValuedAdapter.setAssets(newData)
            binding.mostValuedRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupState() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.CONTENT -> {
                    binding.fragmentMostValuedState.visibility = View.INVISIBLE
                    binding.mostValuedRecyclerView.visibility = View.VISIBLE
                }
                State.LOADING -> {
                    val loadingFragment = LoadingFragment()
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_most_valued_state, loadingFragment)

                    binding.fragmentMostValuedState.visibility = View.VISIBLE
                    binding.mostValuedRecyclerView.visibility = View.INVISIBLE
                }
                State.ERROR -> {
                    val errorFragment = ErrorFragment(this)
                    (activity as? MainActivity)?.replaceFragment(R.id.fragment_most_valued_state, errorFragment)

                    binding.fragmentMostValuedState.visibility = View.VISIBLE
                    binding.mostValuedRecyclerView.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun didTryAgainClicked() {
        viewModel.getAssets()
    }
}
