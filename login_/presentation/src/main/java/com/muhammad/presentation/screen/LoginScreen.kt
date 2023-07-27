package com.muhammad.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.core.core_ui.Constant
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muhammad.presentation.R
import com.muhammad.presentation.component.InputField
import com.muhammad.presentation.component.SaveButton
import com.muhammad.presentation.event.LoginEvent
import com.muhammad.presentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navController : NavController,
    destination: String,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = false
    val state = viewModel.state
    val res = viewModel.loginState.value
    val context = LocalContext.current
    if (res.loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (res.error.isNotBlank()) {
        Toast.makeText(context,res.error,Toast.LENGTH_LONG).show()
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text(text = res.error)
//        }
    }


    LaunchedEffect(key1 = res.token) {
        if (res.token.isNullOrEmpty().not()){
            navController.navigate(destination)
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(com.muhammad.core_ui.R.drawable.bg),
                    contentDescription = "Image",
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.1f)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                InputField(
                    text = state.userNameOremail,
                    valueState = { viewModel.onEvent(LoginEvent.EnterEmail(it)) },
                    labelId = "Email",
                    enable = true,
                    error = false,
                    isSingleLine = true,
                )
                if (!state.emailError.isNullOrEmpty()) {
                    Text(
                        text = state.emailError.toString(),
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                InputField(
                    text = state.password,
                    valueState = { viewModel.onEvent(LoginEvent.EnterPassword(it)) },
                    labelId = stringResource(R.string.password),
                    enable = true,
                    error = false,
                    isSingleLine = true,
                )
                if (!state.passwordError.isNullOrEmpty()) {
                    Text(
                        text = state.passwordError.toString(),
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                SaveButton(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp)
                    .padding(2.dp), text = "Continue", onClick = {
                    viewModel.onEvent(LoginEvent.Login)
                })
                Text(
                    text = "NEED HELP?",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 35.dp)
                )
            }
        }
    }
}

@Composable
fun PreviewLogin(name: String, modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
//    LoginScreen()
}