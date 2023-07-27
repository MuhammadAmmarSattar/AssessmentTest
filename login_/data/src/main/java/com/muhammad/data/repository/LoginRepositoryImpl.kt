package com.muhammad.data.repository

import com.muhammad.network.model.request.LoginRequest
import com.muhammad.data.network.LoginAPI
import com.muhammad.domain.repository.LoginRepository
import com.muhammad.network.model.response.LoginResponse

class LoginRepositoryImpl(private val loginAPI: LoginAPI) : LoginRepository {

    override suspend fun login(loginRequest: LoginRequest): LoginResponse {

        return try {
         loginAPI.login(loginRequest = loginRequest)
//            LoginResponse.mock()
        } catch (e: Exception) {
         LoginResponse(token = "", error = "Email and password not registered")
        }
    }
}