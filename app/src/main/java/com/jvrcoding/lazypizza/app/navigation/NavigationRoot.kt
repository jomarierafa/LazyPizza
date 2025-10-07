package com.jvrcoding.lazypizza.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvrcoding.lazypizza.product.presentation.product_list.ProductScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Product
    ) {
        composable<NavigationRoute.Product> {
            ProductScreenRoot()
        }
    }
}