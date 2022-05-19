package com.luisZimermann.cardapioVirtualM2.service

import com.luisZimermann.cardapioVirtualM2.controller.ApiController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Essa tag é apenas para identificar os erros no LOG
    const val TAG = "API - ERROR"

    val api: ApiController by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiController::class.java)
    }
}