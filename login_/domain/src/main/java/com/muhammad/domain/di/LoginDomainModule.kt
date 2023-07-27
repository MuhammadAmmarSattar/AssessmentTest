package com.muhammad.domain.di

import com.muhammad.domain.repository.LoginRepository
import com.muhammad.domain.usecase.LoginUseCase
import com.muhammad.domain.usecase.ValidateEmail
import com.muhammad.domain.usecase.ValidatePassword
import com.muhammad.domain.usecase.ValidateUserName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object LoginDomainModule {

    @Provides
    fun provideSignUpUseCase(loginRepository: LoginRepository):LoginUseCase{
        return LoginUseCase(loginRepository)
    }

    @Provides
    fun provideEmailValidate() : ValidateEmail = ValidateEmail()

    @Provides
    fun providePasswordValidate() : ValidatePassword = ValidatePassword()

    @Provides
    fun provideUserNameValidate() : ValidateUserName = ValidateUserName()
}