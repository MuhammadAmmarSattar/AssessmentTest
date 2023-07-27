package com.muhammad.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.core_ui.Resource
import com.muhammad.domain.usecase.LoginUseCase
import com.muhammad.domain.usecase.ValidateEmail
import com.muhammad.domain.usecase.ValidatePassword
import com.muhammad.domain.usecase.ValidateUserName
import com.muhammad.network.model.request.LoginRequest
import com.muhammad.presentation.event.LoginEvent
import com.muhammad.presentation.state.LoginState
import com.muhammad.presentation.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateUserName: ValidateUserName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
) : ViewModel() {


    var state by mutableStateOf(UserState())
    private val _signUpState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _signUpState


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterEmail -> {
                state = state.copy(userNameOremail = event.email)
            }

            is LoginEvent.EnterPassword -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.Login -> {
                val userNameResult = validateUserName.execute(state.userNameOremail)
                val passwordResult = validatePassword.execute(state.password)

                val hasError = listOf(
                    userNameResult,
                    passwordResult
                ).any { !it.successful }
                if (hasError) {
                    state = state.copy(
                        emailError = userNameResult.errorMessage,
                        passwordError = passwordResult.errorMessage
                    )
                    return
                } else {
                    state = state.copy(
                        emailError = userNameResult.errorMessage,
                        passwordError = passwordResult.errorMessage
                    )
                    loginUseCase.invoke(
                        loginRequest =
                        LoginRequest(
                            username = state.userNameOremail,
                            password = state.password,
                        )
                    ).onEach {
                        when (it) {
                            is Resource.Loading -> {
                                _signUpState.value = LoginState(loading = true)
                            }

                            is Resource.Error -> {
                                _signUpState.value = LoginState(error = it.message)
                            }

                            is Resource.Success -> {

                                  it.data?.token.let {
                                    _signUpState.value = LoginState(token = it)
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

}