package com.luisZimermann.cardapioVirtualM2.controller

import com.luisZimermann.cardapioVirtualM2.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiController {

    @GET("/product")
    suspend fun getAllProducts(): Response<List<ProductModel>>
}