package com.muhammad.presentation.state


data class LoginState(
    val loading: Boolean = false,
    val error: String = "",
    val isSuccessful: Boolean = false,
    val token : String?=null
)
