package com.muhammad.domain.repository

import com.muhammad.network.model.request.LoginRequest
import com.muhammad.network.model.response.LoginResponse


interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest): LoginResponse
}