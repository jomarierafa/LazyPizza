package com.jvrcoding.lazypizza.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvrcoding.lazypizza.product.presentation.ProductMainScreenRoot
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsScreenRoot
import com.jvrcoding.lazypizza.product.presentation.util.toProductDetailsRoute

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Main
    ) {

        composable<NavigationRoute.Main> {
            ProductMainScreenRoot(
                onNavigateToProductDetails = { product ->
                    navController.navigate(product.toProductDetailsRoute())
                }
            )
        }

        composable<NavigationRoute.ProductDetails> {
            ProductDetailsScreenRoot(
                onBackClick = navController::navigateUp
            )
        }
    }
}