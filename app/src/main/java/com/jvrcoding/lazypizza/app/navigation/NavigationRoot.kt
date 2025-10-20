package com.jvrcoding.lazypizza.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvrcoding.lazypizza.product.presentation.MainScreenRoot
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Main
    ) {
//        composable<NavigationRoute.Product> {
//            ProductScreenRoot(
//                onNavigateToProductDetails = { product ->
//                    navController.navigate(product.toProductDetailsRoute())
//                }
//            )
//        }

        composable<NavigationRoute.Main> {
            MainScreenRoot()
        }

        composable<NavigationRoute.ProductDetails> {
            ProductDetailsScreenRoot(
                onBackClick = navController::navigateUp
            )
        }
    }
}