package com.jvrcoding.lazypizza.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.jvrcoding.lazypizza.auth.presentation.authentication.AuthenticationScreenRoot
import com.jvrcoding.lazypizza.product.presentation.ProductMainScreenRoot
import com.jvrcoding.lazypizza.product.presentation.checkout.CheckoutScreenRoot
import com.jvrcoding.lazypizza.product.presentation.model.Tab
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsScreenRoot
import com.jvrcoding.lazypizza.product.presentation.util.toProductDetailsRoute

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationGraph.ProductGraph
    ) {
        authGraph(
            navController = navController
        )
        productGraph(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.authGraph(
    navController: NavHostController
) {
    navigation<NavigationGraph.AuthGraph>(
        startDestination = NavigationRoute.Authentication
    ) {
        composable<NavigationRoute.Authentication> {
            AuthenticationScreenRoot(
                onNavigateToMenuScreen = {
                    navController.navigate(NavigationRoute.Main(Tab.MENU)) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.productGraph(
    navController: NavHostController
) {
    navigation<NavigationGraph.ProductGraph>(
        startDestination = NavigationRoute.Main(Tab.MENU)
    ) {
        composable<NavigationRoute.Main> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.Main>()
            ProductMainScreenRoot(
                tab = route.tab,
                onNavigateToProductDetails = { product ->
                    navController.navigate(product.toProductDetailsRoute())
                },
                onNavigateToAuthentication = {
                    navController.navigate(NavigationGraph.AuthGraph)
                },
                onNavigateToCheckoutScreen = {
                    navController.navigate(NavigationRoute.Checkout)
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

        composable<NavigationRoute.Checkout> {
            CheckoutScreenRoot(
                onBackClick = navController::navigateUp,
                onBackToMenuClick = {
                    navController.navigate(NavigationRoute.Main(Tab.MENU)) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
