package com.example.cryptoapp.common.fragments.searchAsset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.common.models.SearchAsset
import com.example.cryptoapp.databinding.AssetViewHolderHighlightsBinding
import com.example.cryptoapp.databinding.FragmentSearchAssetBinding
import com.example.cryptoapp.databinding.ViewHolderSearchAssetBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchAssetAdapter(private var searchAsset : List<SearchAsset> = ArrayList()) : RecyclerView.Adapter<SearchAssetViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAssetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSearchAssetBinding.inflate(inflater, parent, false)

        return SearchAssetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return searchAsset.size
    }

    override fun onBindViewHolder(holder: SearchAssetViewHolder, position: Int) {
        when(holder) {
            is SearchAssetViewHolder -> {
                holder.bind(searchAsset[position])
            }
        }
    }
}


class SearchAssetViewHolder(private val binding: ViewHolderSearchAssetBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(searchAsset: SearchAsset) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(binding.iconImageView)
            .applyDefaultRequestOptions(requestOptions)
            .load(searchAsset.icon)
            .into(binding.iconImageView)
        binding.symbolTextView.text = searchAsset.symbol
        binding.nameTextView.text = searchAsset.name
    }
}