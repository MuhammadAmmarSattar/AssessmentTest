package com.muhammad.network.model.response

data class LoginResponse(
    val token : String,
    val error : String ?=null
)