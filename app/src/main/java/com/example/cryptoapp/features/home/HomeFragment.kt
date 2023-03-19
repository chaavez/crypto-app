package com.example.cryptoapp.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.AssetViewHolderBinding
import com.example.cryptoapp.databinding.FragmentHomeBinding
import com.example.cryptoapp.models.Asset
import com.example.cryptoapp.models.AssetViewHolder
import com.example.cryptoapp.models.Mock
import com.example.cryptoapp.models.MostValuedAdapter

import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mostValuedAdapter: MostValuedAdapter
    private lateinit var recyclerView: RecyclerView

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
        val assets = getAssets()
        this.mostValuedAdapter.setAssets(assets)
    }

    private fun getAssets(): ArrayList<Asset> {
        try {
            val url = URL("https://crypto-could-i-have-won-production.up.railway.app/assets")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 30000
            urlConnection.connectTimeout = 30000
            urlConnection.connect()

            val inputStream = BufferedInputStream(urlConnection.inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while(bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            inputStream.close()
            urlConnection.disconnect()
            val response = stringBuilder.toString()

            val json = JSONArray(response)
            val assets = ArrayList<Asset>()
            for(i in 0 until json.length()) {
                val jsonObject = json.getJSONObject(i)
                val asset = Asset(
                    jsonObject.getString("symbol"),
                    jsonObject.getString("nome"),
                    jsonObject.getString("icon"),
                    jsonObject.getDouble("price"),
                    jsonObject.getDouble("variation"),
                )
                assets.add(asset)
                return assets
            }
        } catch (e: Exception) {
            e.message
        }
        return ArrayList<Asset>()
    }
}