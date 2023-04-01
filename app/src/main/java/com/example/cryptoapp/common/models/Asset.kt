package com.example.cryptoapp.common.models

data class Asset(
    val symbol: String,
    val name: String,
    val icon: String,
    val price: Double,
    val variation: Double
)