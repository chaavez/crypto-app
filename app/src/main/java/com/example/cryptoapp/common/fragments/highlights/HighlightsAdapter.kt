package com.example.cryptoapp.common.fragments.highlights

import android.content.Context
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

        val layoutParams = binding.root.layoutParams
        layoutParams.width = getScreenWidth(parent.context) / 2
        binding.root.layoutParams = layoutParams

        return AssetViewHolderHighlights(binding)
    }

    override fun getItemCount() = assets.size

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

    private fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
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
        binding.variationTextView.text = "${roundTheNumber(asset.variation)}%"
    }

    fun roundTheNumber(numInDouble: Double): String {
        return "%.2f".format(numInDouble)
    }
}