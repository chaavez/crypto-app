package com.example.cryptoapp.database.dao

import androidx.room.*
import com.example.cryptoapp.database.entity.AssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Insert
    suspend fun insert(assetEntity: AssetEntity)

    @Query("SELECT * FROM assets")
    fun getAll(): Flow<List<AssetEntity>>

    @Update
    suspend fun update(assetEntity: AssetEntity)

    @Delete
    suspend fun delete(assetEntity: AssetEntity)

}