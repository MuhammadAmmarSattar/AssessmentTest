package com.muhammad.data.network

import com.muhammad.network.model.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): com.muhammad.network.model.response.LoginResponse
}