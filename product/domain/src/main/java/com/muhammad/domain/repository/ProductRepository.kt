package com.muhammad.domain.repository

import com.core.network.model.Product

interface ProductRepository {

    suspend fun getProducts(): List<Product>
}