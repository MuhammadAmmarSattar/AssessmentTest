package com.muhammad.data.di

import com.muhammad.data.network.ProductAPI
import com.muhammad.data.repository.ProductRepositoryImpls
import com.muhammad.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ProductDataModule {
    @Provides
    fun provideLoginApi(retrofit: Retrofit): ProductAPI {
        return retrofit.create(ProductAPI::class.java)
    }


    @Provides
    fun provideLoginRepository(loginAPI: ProductAPI): ProductRepository {
        return ProductRepositoryImpls(loginAPI)

    }
}