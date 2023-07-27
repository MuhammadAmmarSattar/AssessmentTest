package com.muhammad.presentation.event

sealed class LoginEvent {
    data class EnterEmail(val email: String): LoginEvent()
    data class EnterPassword(val password: String): LoginEvent()
    object Login: LoginEvent()
}