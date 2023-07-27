package com.muhammad.assessmenttest

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.core.network.model.Product
import com.muhammad.presentation.details.ProductDetail
import com.muhammad.presentation.productScreen.BottomBar
import com.muhammad.presentation.screen.LoginScreen

@Composable
fun NanoMainScreen() {

    val navController = rememberNavController()

    val colors = MaterialTheme.colors
    var statusBarColor by remember { mutableStateOf(colors.primaryVariant) }
    var navigationBarColor by remember { mutableStateOf(colors.primaryVariant) }


    NavHost(navController = navController, startDestination = NavScreen.Login.route) {
        composable(NavScreen.Login.route) {
            LoginScreen(navController, destination = NavScreen.Home.route)
        }

        composable(NavScreen.Home.route) {
            BottomBar(
                viewModel = hiltViewModel(),
                selectedProduct = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("product", it)
                    navController.navigate(NavScreen.PosterDetails.route)
                }
            )
            LaunchedEffect(Unit) {
                statusBarColor = colors.primaryVariant
                navigationBarColor = colors.primaryVariant
            }
        }
        composable(
            route = NavScreen.PosterDetails.route,
        ) { backStackEntry ->
            val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            product?.let {
                ProductDetail(productDetail = product) {
                    navController.navigateUp()
                }
            }
        }
    }

}

sealed class NavScreen(val route: String) {
    object Login : NavScreen("Login")

    object Home : NavScreen("Home")

    object PosterDetails : NavScreen("PosterDetails") {

//        const val routeWithArgument: String = "PosterDetails/{posterId}"

        const val argument0: String = "posterId"
    }
}