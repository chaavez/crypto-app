package com.example.cryptoapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class AssetEntity(
    @ColumnInfo(name = "symbol") val symbol: String?,
    @PrimaryKey val name: String,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "price") val price: Double?,
    @ColumnInfo(name = "variation") val variation: Double?,
    @ColumnInfo(name = "amount") val amount: String?,
    @ColumnInfo(name = "date") val date: String?
)