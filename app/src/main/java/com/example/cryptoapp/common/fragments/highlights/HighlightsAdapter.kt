package com.example.cryptoapp.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.AssetViewHolderHighlightsBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class HighlightsAdapter(private var assets : List<Asset> = ArrayList()) : RecyclerView.Adapter<AssetViewHolderHighlights>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolderHighlights {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AssetViewHolderHighlightsBinding.inflate(inflater, parent, false)
        return AssetViewHolderHighlights(binding)
    }

    override fun getItemCount(): Int {
        return assets.size
    }

    fun setAssets(asset: List<Asset>) {
        this.assets = asset
    }

    override fun onBindViewHolder(holder: AssetViewHolderHighlights, position: Int) {
        when(holder) {
            is AssetViewHolderHighlights -> {
                holder.bind(assets[position])
            }
        }
    }
}

class AssetViewHolderHighlights constructor(private val binding: AssetViewHolderHighlightsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun Double.formatMoney(currencyCode: String = "USD", locale: Locale = Locale.US): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance(currencyCode)
        return format.format(this)
    }

    fun bind(asset: Asset) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        val priceValue = asset.price
        val formattedValue = priceValue.formatMoney("BRL", Locale("pt", "BR"))

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
        binding.nameTextView.text = asset.name
        binding.priceTextView.text = formattedValue
        binding. variationTextView.text = "$variationValue%"
    }
}