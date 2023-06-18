package com.example.cryptoapp.database.dao

import androidx.room.*
import com.example.cryptoapp.database.entity.AssetEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assetEntity: AssetEntity)

    @Query("SELECT * FROM assets")
    fun getAll(): Flow<List<AssetEntity>>

    @Query("SELECT SUM(price) FROM assets")
    fun getTotalInvestment(): Flow<Double>

    @Query("SELECT * FROM assets WHERE name = :name")
    suspend fun getAssetByName(name: String): AssetEntity?

    @Update
    suspend fun updateAsset(assetEntity: AssetEntity)

    @Delete
    suspend fun delete(assetEntity: AssetEntity)

    @Transaction
    suspend fun updateTotalInvestment() {
        val assets = getAll().first()
        val totalInvestment = assets.sumOf { assetEntity ->
            assetEntity.price.toDouble()
        }

        assets.forEach { asset ->
            asset.totalInvestment = totalInvestment.toString()
            updateAsset(asset)
        }
    }
}