package com.muhammad.presentation.state

import com.core.network.model.LoginResponse

data class LoginState(
    val loading: Boolean = false,
    val error: String = "",
    val isSuccessful: Boolean = false,
    val token : String?=null
)
