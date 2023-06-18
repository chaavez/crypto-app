package com.example.cryptoapp.features.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.database.entity.AssetEntity
import com.example.cryptoapp.databinding.AssetViewHolderWalletBinding

class WalletAdapter(private var assets: List<WalletViewModel.AssetItem> = ArrayList()) : RecyclerView.Adapter<WalletViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AssetViewHolderWalletBinding.inflate(inflater, parent, false)

        return WalletViewHolder(binding)
    }

    override fun getItemCount() = assets.size

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(assets[position])
    }

    fun setAssets(asset: List<WalletViewModel.AssetItem>) {
        assets = asset
        notifyDataSetChanged()
    }
}

class WalletViewHolder(private val binding: AssetViewHolderWalletBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(asset: WalletViewModel.AssetItem) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(binding.iconImageView)
            .applyDefaultRequestOptions(requestOptions)
            .load(asset.assetEntity.icon)
            .into(binding.iconImageView)
        binding.symbolTextView.text = asset.assetEntity.symbol
        binding.nameTextView.text = asset.assetEntity.name
        binding.variationTextView.text = asset.assetEntity.variation
        binding.investedValueTextView.text = asset.assetEntity.totalInvestmentAsset
    }
}
