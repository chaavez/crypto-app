package com.example.cryptoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptoapp.database.dao.AssetDao
import com.example.cryptoapp.database.entity.AssetEntity

@Database(entities = [AssetEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun assetDao() : AssetDao
}