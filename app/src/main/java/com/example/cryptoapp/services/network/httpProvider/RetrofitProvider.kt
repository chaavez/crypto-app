package com.example.cryptoapp.services.network.httpProvider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider {
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiBaseURL.BASE_URL)
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun client() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }
}