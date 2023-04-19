package com.example.cryptoapp.models

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cryptoapp.R
import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.databinding.AssetViewHolderMostValuedBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MostValuedAdapter(private var assets : List<Asset> = ArrayList()) : RecyclerView.Adapter<AssetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AssetViewHolderMostValuedBinding.inflate(inflater, parent, false)
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

class AssetViewHolder constructor(private val binding: AssetViewHolderMostValuedBinding) : RecyclerView.ViewHolder(binding.root) {

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

        val formattedValue = asset.price.formatMoney("BRL", Locale("pt", "BR"))

        if(asset.variation > 0) {
            binding.variationTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green_100))
        } else if(asset.variation.equals(0.0)) {
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
        binding. variationTextView.text = "${roundTheNumber(asset.variation)}%"
    }

    fun roundTheNumber(numInDouble: Double): String {
        return "%.2f".format(numInDouble)
    }
}