package com.muhammad.domain.usecase

import com.core.core_ui.Resource
import com.muhammad.domain.repository.LoginRepository
import com.muhammad.network.model.request.LoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    operator fun invoke(loginRequest : LoginRequest) : Flow<Resource<com.muhammad.network.model.response.LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data=loginRepository.login(loginRequest)))
        }catch (e:Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}