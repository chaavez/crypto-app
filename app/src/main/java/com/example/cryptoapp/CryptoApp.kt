package com.example.cryptoapp

import android.app.Application
import androidx.room.Room
import com.example.cryptoapp.database.AppDataBase
import com.example.cryptoapp.database.dao.AssetDao

class CryptoApp : Application() {
    companion object {
        private lateinit var database : AppDataBase

        fun getAssetDao(): AssetDao {
            return database.assetDao()
        }
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext,
        AppDataBase::class.java,
            "cryptoApp-database"
        ).build()
    }
}