package com.example.cryptoapp.common.fragments.searchAsset

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.ViewHolderSearchAssetBinding
import kotlin.collections.ArrayList

interface SearchAssetAdapterListener {
    fun didAssetClicked(asset: Asset)
}

class SearchAssetAdapter(private var assets : List<Asset> = ArrayList(), private val listener: SearchAssetAdapterListener) : RecyclerView.Adapter<SearchAssetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAssetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSearchAssetBinding.inflate(inflater, parent, false)

        return SearchAssetViewHolder(binding)
    }

    override fun getItemCount() = assets.size

    override fun onBindViewHolder(holder: SearchAssetViewHolder, position: Int) {
        holder.bind(assets[position], listener)
    }

    fun setAssets(asset: List<Asset>) {
        this.assets = asset
        notifyDataSetChanged()
    }
}

class SearchAssetViewHolder(private val binding: ViewHolderSearchAssetBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(asset: Asset, listener: SearchAssetAdapterListener) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(binding.iconImageView)
            .applyDefaultRequestOptions(requestOptions)
            .load(asset.icon)
            .into(binding.iconImageView)
        binding.symbolTextView.text = asset.symbol
        binding.nameTextView.text = asset.name

        binding.root.setOnClickListener {
            listener.didAssetClicked(asset)
        }
    }
}