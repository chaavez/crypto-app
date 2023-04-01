package com.example.cryptoapp.services.network.apis.assets

import com.example.cryptoapp.common.models.Asset
import com.example.cryptoapp.services.network.httpProvider.APIUserErrors
import com.example.cryptoapp.services.network.httpProvider.ApiError
import com.example.cryptoapp.services.network.httpProvider.RetrofitProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface AssetsApi {
    @GET("assets")
    fun assets(): Call<ResponseBody>
}

class AssetsRequest(val serviceProvider: RetrofitProvider) {
    fun get(onResult: (List<AssetResponse>?, ApiError?) -> Unit) {
        val request = serviceProvider.retrofit().create(AssetsApi::class.java)

        request.assets().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val json = response.body()?.string()
                    val assets: List<AssetResponse> = Gson().fromJson(json, object : TypeToken<List<AssetResponse>>() {}.type)
                    onResult(assets, null)
                } else {
                    onResult(null, ApiError(APIUserErrors.UNEXPECTED, "Erro!!!!!!!"))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                onResult(null, throwable.message?.let { ApiError(APIUserErrors.GENERIC, it) })
            }
        })
    }
}