package com.muhammad.presentation.state

data class UserState(
    val userNameOremail: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)
