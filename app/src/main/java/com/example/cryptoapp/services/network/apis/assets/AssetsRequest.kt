package com.example.cryptoapp.services.network.apis.assets

import com.example.cryptoapp.services.network.httpProvider.APIError
import com.example.cryptoapp.services.network.httpProvider.RetrofitProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AssetsApi {
    @GET("assets")
    fun assets(): Call<ResponseBody>

    @GET("asset/{symbol}/{date}")
    fun asset(@Path("symbol") symbol: String, @Path("date") date: String): Call<ResponseBody>
}

class AssetsRequest(private val serviceProvider: RetrofitProvider) {
    fun getAssets(onResult: (List<AssetResponse>?, APIError?) -> Unit) {
        val request = serviceProvider.retrofit().create(AssetsApi::class.java)

        request.assets().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val json = response.body()?.string()
                    val assets: List<AssetResponse> = Gson().fromJson(json, object : TypeToken<List<AssetResponse>>() {}.type)
                    onResult(assets, null)
                } else {
                    onResult(null, APIError.GENERIC)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                onResult(null, throwable.message?.let { APIError.GENERIC })
            }
        })
    }

    fun getAsset(symbol: String, date: String, onResult: (AssetResponse?, APIError?) -> Unit) {
        val request = serviceProvider.retrofit().create(AssetsApi::class.java)

        request.asset(symbol, date).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val json = response.body()?.string()
                    val asset: AssetResponse = Gson().fromJson(json, object : TypeToken<AssetResponse>() {}.type)
                    onResult(asset, null)
                } else {
                    onResult(null, APIError.GENERIC)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                onResult(null, throwable.message?.let { APIError.GENERIC })
            }
        })
    }
}