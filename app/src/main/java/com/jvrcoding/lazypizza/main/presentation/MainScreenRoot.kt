package com.jvrcoding.lazypizza.main.presentation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.main.presentation.components.LPBottomNavBar
import com.jvrcoding.lazypizza.main.presentation.components.LPNavigationRail
import com.jvrcoding.lazypizza.main.presentation.navigation.MainNavigationRoute
import com.jvrcoding.lazypizza.main.presentation.navigation.Tab
import com.jvrcoding.lazypizza.product.presentation.product_list.ProductScreenRoot



@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreenRoot() {
    val mainNavController = rememberNavController()

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }


    val activity = LocalActivity.current as ComponentActivity
    val windowSizeClass = calculateWindowSizeClass(activity = activity)
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            Scaffold(
                bottomBar = {
                    LPBottomNavBar(
                        onItemClick = {},
                        selectedTab = Tab.Menu
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = mainNavController,
                    startDestination = MainNavigationRoute.Menu,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable<MainNavigationRoute.Menu> {
                        ProductScreenRoot(
                            onNavigateToProductDetails = { product ->
//                                navController.navigate(product.toProductDetailsRoute())
                            }
                        )
                    }
                    composable<MainNavigationRoute.Cart> {
                    }
                    composable<MainNavigationRoute.History> {
                    }
                }

            }
        }
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Row(modifier = Modifier.fillMaxSize()) {
                LPNavigationRail(
                    onItemClick = {},
                    selectedTab = Tab.Menu
                )
            }
        }
    }
}