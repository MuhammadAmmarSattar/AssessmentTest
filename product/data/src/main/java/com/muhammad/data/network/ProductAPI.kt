package com.muhammad.data.network

import com.core.network.model.Product
import retrofit2.http.GET

interface ProductAPI {

    @GET("/products")
    suspend fun fetchAllProduct(): List<Product>
}