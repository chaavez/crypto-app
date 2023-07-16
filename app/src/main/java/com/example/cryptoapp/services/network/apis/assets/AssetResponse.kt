package com.example.cryptoapp.services.network.apis.assets

data class AssetResponse(
    val symbol: String,
    val name: String,
    val icon: String,
    val price: Double,
    val variation: Double
)