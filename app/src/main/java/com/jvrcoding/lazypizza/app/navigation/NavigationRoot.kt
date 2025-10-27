package com.jvrcoding.lazypizza.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jvrcoding.lazypizza.product.presentation.ProductMainScreenRoot
import com.jvrcoding.lazypizza.product.presentation.model.Tab
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsScreenRoot
import com.jvrcoding.lazypizza.product.presentation.util.toProductDetailsRoute

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Main(Tab.MENU)
    ) {
        composable<NavigationRoute.Main> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.Main>()
            ProductMainScreenRoot(
                tab = route.tab,
                onNavigateToProductDetails = { product ->
                    navController.navigate(product.toProductDetailsRoute())
                }
            )
        }

        composable<NavigationRoute.ProductDetails> {
            ProductDetailsScreenRoot(
                onNavigateToCartScreen = {
                    navController.navigate(NavigationRoute.Main(Tab.CART)) {
                        popUpTo(0)
                    }
                },
                onBackClick = navController::navigateUp
            )
        }
    }
}
