package com.example.cryptoapp.common.models

data class Asset(
    val symbol: String,
    val name: String,
    val icon: String,
    val price: Double,
    val variation: Double
)

class FixedAssets {
    companion object {
        fun BTC(): Asset {
            return Asset(
                "BTC",
                "Bitcoin",
                "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
                140000.0,
                0.0
            )
        }
    }
}