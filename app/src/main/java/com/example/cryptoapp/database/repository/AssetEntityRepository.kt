package com.example.cryptoapp.database.repository

import com.example.cryptoapp.database.dao.AssetDao
import com.example.cryptoapp.database.entity.AssetEntity
import kotlinx.coroutines.flow.Flow

class AssetEntityRepository(private val assetDao: AssetDao) {
    suspend fun insertAsset(assetEntity: AssetEntity) {
        assetDao.insert(assetEntity)
    }

    fun getAllAssets(): Flow<List<AssetEntity>> {
        return assetDao.getAll()
    }

    suspend fun updateAsset(assetEntity: AssetEntity) {
        assetDao.update(assetEntity)
    }

    suspend fun deleteAsset(assetEntity: AssetEntity) {
        assetDao.delete(assetEntity)
    }
}