package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.models.Asset
import com.example.cryptoapp.models.Mock
import com.example.cryptoapp.models.MostValuedAdapter
import com.example.cryptoapp.services.network.NetworkTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mostValuedAdapter: MostValuedAdapter

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupLayout()
        initMostValued()
        addAssets()

        return root
    }

    private fun setupLayout() {
        binding.toolbar.titleTextView.text = getString(R.string.home_title)
    }

    private fun initMostValued() {
        mostValuedAdapter = MostValuedAdapter()
        binding.mostValuedRecyclerView.adapter = mostValuedAdapter
        binding.mostValuedRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun addAssets() {
        getAssets { it
            this.mostValuedAdapter.setAssets(it)
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
                    temporaryAsset.getString("nome"),
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