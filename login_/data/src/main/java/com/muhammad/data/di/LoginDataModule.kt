package com.muhammad.data.di

import com.muhammad.data.network.LoginAPI
import com.muhammad.data.repository.LoginRepositoryImpl
import com.muhammad.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object LoginDataModule {

    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginAPI {
        return retrofit.create(LoginAPI::class.java)
    }


    @Provides
    fun provideLoginRepository(loginAPI: LoginAPI): LoginRepository {
        return LoginRepositoryImpl(loginAPI)
    }

}