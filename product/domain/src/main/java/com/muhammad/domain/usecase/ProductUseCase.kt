package com.muhammad.domain.usecase

import com.core.core_ui.Resource
import com.core.network.model.Product
import com.muhammad.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    operator fun invoke() : Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data=productRepository.getProducts()))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}