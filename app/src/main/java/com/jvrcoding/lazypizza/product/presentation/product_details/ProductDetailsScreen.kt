package com.jvrcoding.lazypizza.product.presentation.product_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.product.presentation.product_details.components.TopSection
import com.jvrcoding.lazypizza.product.presentation.product_details.components.ToppingSection
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProductDetailsScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                onBackClick = {}
            )
        }
    ) { innerPadding ->
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT -> {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    TopSection(
                        imageUrl = "",
                        productName = "Margherita",
                        description = "Tomato sauce, Mozzarella, Fresh basil, Olive oil"
                    )
                    ToppingSection(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                        toppingList = (1..20).map {
                            ToppingUi(
                                id = it.toString(),
                                name = "Topping $it",
                                description = "Description $it",
                                price = "$ $it.50",
                                imageUrl = ""
                            )
                        }
                    )
                }
            }
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                Row(
                    modifier = modifier
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 16.dp),
                ) {
                    TopSection(
                        modifier = Modifier.weight(1f),
                        imageUrl = "",
                        productName = "Margherita",
                        description = "Tomato sauce, Mozzarella, Fresh basil, Olive oil"
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(
                                topStart = 16.dp,
                                bottomStart = 16.dp),
                            )
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(vertical = 16.dp)
                            .weight(1f),
                    ) {
                        ToppingSection(
                            toppingList = (1..20).map {
                                ToppingUi(
                                    id = it.toString(),
                                    name = "Topping $it",
                                    description = "Description $it",
                                    price = "$ $it.50",
                                    imageUrl = ""
                                )
                            }
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun ProductDetailScreenPreview() {
    LazyPizzaTheme {
        ProductDetailsScreen()
    }
}