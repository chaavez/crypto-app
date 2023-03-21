package com.example.cryptoapp.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.AssetViewHolderBinding
import java.text.DecimalFormat

class MostValuedAdapter(private var assets : List<Asset> = ArrayList()) : RecyclerView.Adapter<AssetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AssetViewHolderBinding.inflate(inflater, parent, false)
        return AssetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    fun setAssets(asset: List<Asset>) {
        this.assets = asset
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

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        val priceValue = asset.price
        val decimalFormatted = DecimalFormat("#.##")
        val priceFormatted = decimalFormatted.format(priceValue)

        val variationValue = asset.variation
        if(variationValue > 0) {
            binding.variationTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_100))
        } else if(variationValue.equals(0.0)) {
            binding.variationTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        } else {
            binding.variationTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.secondary_200))
        }

        Glide.with(binding.iconImageView)
            .applyDefaultRequestOptions(requestOptions)
            .load(asset.icon)
            .into(binding.iconImageView)
        binding.symbolTextView.text = asset.symbol
        binding.nameTextView.text = asset.nome
        binding.priceTextView.text = priceFormatted
        binding. variationTextView.text = "$variationValue%"
    }
}