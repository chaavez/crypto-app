package com.example.cryptoapp.features.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.models.Asset
import com.example.cryptoapp.models.HighlightsAdapter
import com.example.cryptoapp.models.MostValuedAdapter
import com.example.cryptoapp.services.network.NetworkTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mostValuedAdapter: MostValuedAdapter
    private lateinit var highlightsAdapter: HighlightsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupLayout()
        initMostValued()
        initHighlights()
        addAssets()
        addAssetsHighlights()

        return root
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.home_title)
    }

    private fun initMostValued() {
        mostValuedAdapter = MostValuedAdapter()
        val mostValuedFragment = MostValuedFragment.newInstance(mostValuedAdapter)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_most_valued, mostValuedFragment)
        fragmentTransaction.commit()
    }

    private fun initHighlights() {
        highlightsAdapter = HighlightsAdapter()
        val highlightsFragment = HighlightsFragment.newInstance(highlightsAdapter)
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container_highlights, highlightsFragment)
        fragmentTransaction.commit()
    }

    private fun addAssets() {
        getAssets { assets ->
            this.mostValuedAdapter.setAssets(assets)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                this.mostValuedAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun addAssetsHighlights() {
        getAssets { assets ->
            this.highlightsAdapter.setAssets(assets)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                this.highlightsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getAssets(callback: (MutableList<Asset>) -> Unit) {
        val network = NetworkTask()
        GlobalScope.launch {
            val result = network.makeRequest("https://crypto-could-i-have-won-production.up.railway.app/assets")
            val jsonArray = JSONArray(result)
            val assets = mutableListOf<Asset>()
            for (i in 0 until jsonArray.length()) {
                val temporaryAsset = jsonArray.getJSONObject(i)
                val asset = Asset(
                    temporaryAsset.getString("symbol"),
                    temporaryAsset.getString("name"),
                    temporaryAsset.getString("icon"),
                    temporaryAsset.getDouble("price"),
                    temporaryAsset.getDouble("variation"),
                )
                assets.add(asset)
            }
            callback(assets)
        }
    }
}