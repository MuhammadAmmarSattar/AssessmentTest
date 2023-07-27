package com.muhammad.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.core.core_ui.Resource
import com.google.common.truth.Truth
import com.muhammad.domain.usecase.LoginUseCase
import com.muhammad.domain.usecase.ValidateEmail
import com.muhammad.domain.usecase.ValidatePassword
import com.muhammad.domain.usecase.ValidateUserName
import com.muhammad.domain.usecase.ValidationResult
import com.muhammad.presentation.event.LoginEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: LoginViewModel

    // stubbing

    private val validateUserName = mock<ValidateUserName> {
        on { execute(any()) } doReturn ValidationResult(
            successful = false,
            errorMessage = "That's not a valid username or email"
        )
    }
    private val validateEmail = mock<ValidateEmail> {
        on { execute(any()) } doReturn ValidationResult(
            successful = false,
            errorMessage = "That's not a valid email"
        )
    }

    private val validatePassword = mock<ValidatePassword> {
        on { execute(any()) } doReturn ValidationResult(
            successful = false,
            errorMessage = "The password needs to contain at least one letter and digit"
        )
    }

    val expectedResponse = com.muhammad.network.model.response.LoginResponse(token = "")

    private val loginUseCase = mock<LoginUseCase> {
        on { invoke(any()) } doReturn
                flowOf(
                    Resource.Success(
                        expectedResponse
                    )
                )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            validateUserName = validateUserName,
            validateEmail = validateEmail,
            validatePassword = validatePassword
        )
    }

    @Test
    fun `onEvent AddPassword updates password`() = runTest {
        viewModel.onEvent(LoginEvent.EnterPassword("Password0"))
        assert(viewModel.state.password == "Password0")
    }


    @Test
    fun `onEvent AddEmail updates Email`() = runTest {
        viewModel.onEvent(LoginEvent.EnterEmail("muhammad@gmail.com"))
        assert(viewModel.state.email == "muhammad@gmail.com")
    }

    @Test
    fun `onEvent AddEmailOrUserName updates emailOrUserName`() = runTest {
        viewModel.onEvent(LoginEvent.EnterEmail("mor_2314"))
        assert(viewModel.state.email == "mor_2314")
    }


    @Test
    fun `onEvent with SubmitUserData should update state with error when email or password validation fails`() =
        runTest {

            viewModel.onEvent(LoginEvent.EnterEmail("ammar"))
            viewModel.onEvent(LoginEvent.EnterPassword("ammar Desc"))
            viewModel.onEvent(LoginEvent.Login)
            Assert.assertEquals("That's not a valid username or email", viewModel.state.emailError)
            Assert.assertEquals(
                "The password needs to contain at least one letter and digit",
                viewModel.state.passwordError
            )
        }

    @Test
    fun `onEvent with SubmitUserData should update state with error when password validation fails`() =
        runTest {
            whenever(validatePassword.execute(any())).thenReturn(
                ValidationResult(
                    successful = false,
                    "The password needs to consist of at least 8 characters"
                )
            )
            viewModel.onEvent(LoginEvent.EnterEmail("ammar@gmail.com"))
            viewModel.onEvent(LoginEvent.EnterPassword("ammar"))
            viewModel.onEvent(LoginEvent.Login)
            Assert.assertEquals(
                "The password needs to consist of at least 8 characters",
                viewModel.state.passwordError
            )
        }


    @Test
    fun `onEvent with SubmitUserData should update state`()  {

        whenever(validateUserName.execute(any())).thenReturn(ValidationResult(successful = true))
        whenever(validatePassword.execute(any())).thenReturn(ValidationResult(successful = true))

        viewModel.onEvent(LoginEvent.EnterEmail("mor_2314"))
        viewModel.onEvent(LoginEvent.EnterPassword("83r5^_"))
        viewModel.onEvent(LoginEvent.Login)

        Truth.assertThat(viewModel.loginState.value.token).isEqualTo(expectedResponse.token)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

}