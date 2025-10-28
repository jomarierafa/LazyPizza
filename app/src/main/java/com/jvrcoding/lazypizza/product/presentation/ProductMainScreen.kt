package com.jvrcoding.lazypizza.product.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.core.presentation.util.ObserveAsEvents
import com.jvrcoding.lazypizza.product.domain.product.Product
import com.jvrcoding.lazypizza.product.presentation.cart.CartScreenRoot
import com.jvrcoding.lazypizza.product.presentation.components.LPBottomNavBar
import com.jvrcoding.lazypizza.product.presentation.components.LPNavigationRail
import com.jvrcoding.lazypizza.product.presentation.menu.MenuScreenRoot
import com.jvrcoding.lazypizza.product.presentation.model.Tab
import com.jvrcoding.lazypizza.product.presentation.navigation.ProductNavigationRoute
import com.jvrcoding.lazypizza.product.presentation.order_history.OrderHistoryScreenRoot
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProductMainScreenRoot(
    tab: Tab = Tab.MENU,
    onNavigateToProductDetails: (Product) -> Unit,
    viewModel: ProductViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val bottomNavController = rememberNavController()
    ObserveAsEvents(flow = viewModel.events) { event ->
        val route = when (event) {
            ProductEvent.NavigateToMenu -> ProductNavigationRoute.Menu
            ProductEvent.NavigateToCart -> ProductNavigationRoute.Cart
            ProductEvent.NavigateToHistory -> ProductNavigationRoute.History
        }
        bottomNavController.navigate(route) {
            popUpTo(bottomNavController.currentDestination!!.id) {
                saveState = true
                inclusive = true
            }
            restoreState = true
        }
    }
    ProductMainScreen(
        startDestination = when(tab) {
            Tab.MENU -> ProductNavigationRoute.Menu
            Tab.CART -> ProductNavigationRoute.Cart
            Tab.HISTORY -> ProductNavigationRoute.History
        },
        navController = bottomNavController,
        state = state,
        onAction = { action ->
            when(action) {
                is ProductAction.OnProductClick -> {
                    onNavigateToProductDetails(action.product)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProductMainScreen(
    startDestination: ProductNavigationRoute,
    navController: NavHostController,
    state: ProductState,
    onAction: (ProductAction) -> Unit,
    deviceConfiguration: DeviceConfiguration? = null
) {

    val currentDeviceConfiguration = deviceConfiguration ?: run {
        val activity = LocalActivity.current as ComponentActivity
        val windowSizeClass = calculateWindowSizeClass(activity = activity)
        DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
    }
    when(currentDeviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            Scaffold(
                bottomBar = {
                    LPBottomNavBar(
                        onItemClick = { tab ->
                            onAction(ProductAction.OnBottomNavigationItemClick(tab))
                        },
                        selectedTab = state.selectedTab,
                        cartItemCount = state.cartItemCount
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                ) {
                    composable<ProductNavigationRoute.Menu> {
                        MenuScreenRoot(
                            onNavigateToProductDetails = { product ->
                                onAction(ProductAction.OnProductClick(product))
                            }
                        )
                    }
                    composable<ProductNavigationRoute.Cart> {
                        CartScreenRoot(
                            onBackToMenuClick = {
                                onAction(ProductAction.OnBottomNavigationItemClick(Tab.MENU))
                            }
                        )
                    }
                    composable<ProductNavigationRoute.History> {
                        OrderHistoryScreenRoot()
                    }
                }

            }
        }
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Row(modifier = Modifier.fillMaxSize()) {
                LPNavigationRail(
                    onItemClick = { tab ->
                        onAction(ProductAction.OnBottomNavigationItemClick(tab))
                    },
                    selectedTab = state.selectedTab,
                    cartItemCount = state.cartItemCount
                )
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                ) {
                    composable<ProductNavigationRoute.Menu> {
                        MenuScreenRoot(
                            onNavigateToProductDetails = { product ->
                                onAction(ProductAction.OnProductClick(product))
                            }
                        )
                    }
                    composable<ProductNavigationRoute.Cart> {
                        CartScreenRoot(
                            onBackToMenuClick = {
                                onAction(ProductAction.OnBottomNavigationItemClick(Tab.MENU))
                            }
                        )
                    }
                    composable<ProductNavigationRoute.History> {
                        OrderHistoryScreenRoot()
                    }
                }

            }
        }
    }
}