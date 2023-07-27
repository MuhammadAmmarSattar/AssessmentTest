package com.muhammad.domain.di

import com.muhammad.domain.repository.ProductRepository
import com.muhammad.domain.usecase.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ProductDomainModule {

    @Provides
    fun provideSignUpUseCase(productRepository: ProductRepository):ProductUseCase{
        return ProductUseCase(productRepository)
    }
}