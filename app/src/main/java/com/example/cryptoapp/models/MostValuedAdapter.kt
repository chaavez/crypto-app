package com.example.cryptoapp.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptoapp.databinding.AssetViewHolderBinding

class MostValuedAdapter(private val assets : List<Asset> = ArrayList()) : RecyclerView.Adapter<AssetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AssetViewHolderBinding.inflate(inflater, parent, false)
        return AssetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        when(holder) {
            is AssetViewHolder -> {
                holder.bind(assets[position])
            }
        }
    }
}

class AssetViewHolder constructor(private val binding: AssetViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(asset: Asset) {
        Glide.with(this.binding.iconImageView).load(asset.icon)
        binding.symbolTextView.text = asset.symbol
        binding.nameTextView.text = asset.name
        binding.priceTextView.text = asset.price.toString()
        binding. variationTextView.text = asset.variation.toString()
    }
}