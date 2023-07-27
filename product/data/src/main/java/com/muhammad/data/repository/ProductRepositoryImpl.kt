package com.muhammad.data.repository

import com.core.network.model.Product
import com.muhammad.data.network.ProductAPI
import com.muhammad.domain.repository.ProductRepository
import kotlinx.coroutines.delay

class ProductRepositoryImpls(private val productAPI: ProductAPI)  : ProductRepository {
    override suspend fun getProducts():  List<Product> {
        return try {
            delay(2000)
            productAPI.fetchAllProduct()
        }catch (e:Exception){
            listOf<Product>()
        }
    }
}