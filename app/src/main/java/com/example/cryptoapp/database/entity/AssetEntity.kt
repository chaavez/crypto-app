package com.example.cryptoapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @ColumnInfo(name = "symbol") val symbol: String?,
    @PrimaryKey val name: String,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "price") var price: String,
    @ColumnInfo(name = "total_investment_in_asset") var totalInvestmentAsset: String,
    @ColumnInfo(name = "total_investment") var totalInvestment: String,
    @ColumnInfo(name = "variation") val variation: String,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "date") val date: String
)