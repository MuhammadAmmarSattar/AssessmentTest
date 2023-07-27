package com.muhammad.presentation.state

import com.core.network.model.Product


data class ProductState(
    val loading: Boolean = false,
    val error: String = "",
    val isSuccessful: Boolean = false,
    val product: List<Product>? = null
)