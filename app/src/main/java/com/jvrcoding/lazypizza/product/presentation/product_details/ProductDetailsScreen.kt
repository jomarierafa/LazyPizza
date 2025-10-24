package com.jvrcoding.lazypizza.product.presentation.product_details

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.product.presentation.product_details.components.TopSection
import com.jvrcoding.lazypizza.product.presentation.product_details.components.ToppingSection
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductDetailsScreenRoot(
    onBackClick: () -> Unit,
    viewModel: ProductDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductDetailsScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is ProductDetailsAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProductDetailsScreen(
    state: ProductDetailsState,
    onAction: (ProductDetailsAction) -> Unit
) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                onBackClick = {
                    onAction(ProductDetailsAction.OnBackClick)
                }
            )
        }
    ) { innerPadding ->

        val activity = LocalActivity.current as ComponentActivity
        val windowSizeClass = calculateWindowSizeClass(activity = activity)
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Column {
                        TopSection(
                            imageUrl = state.imageUrl,
                            productName = state.productName,
                            description = state.description,
                            bottomColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                        ToppingSection(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                            toppingList = state.toppings,
                            selectedTopping = state.selectedToppings,
                            onAction = onAction
                        )
                    }
                    
                    PrimaryButton(
                        text = stringResource(R.string.add_to_cart_for, state.totalPrice),
                        enabled = true,
                        onClick = {
                            onAction(ProductDetailsAction.OnAddToCartClick)
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            }
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                Row(
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 16.dp),
                ) {
                    TopSection(
                        modifier = Modifier.weight(1f),
                        imageUrl = state.imageUrl,
                        productName = state.productName,
                        description = state.description,
                    )
                    Box(
                        modifier = Modifier
                            .dropShadow(
                                shape = RectangleShape,
                                shadow = Shadow(
                                    radius = 16.dp,
                                    spread = 0.dp,
                                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                                    offset = DpOffset(x = (-4).dp, 0.dp)
                                )
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    bottomStart = 16.dp
                                ),
                            )
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            .padding(vertical = 16.dp)
                            .weight(1f),
                    ) {
                        ToppingSection(
                            toppingList = state.toppings,
                            selectedTopping = state.selectedToppings,
                            onAction = onAction,
                            modifier = Modifier.fillMaxHeight()
                        )
                        PrimaryButton(
                            text = stringResource(R.string.add_to_cart_for, state.totalPrice),
                            enabled = true,
                            onClick = {
                                onAction(ProductDetailsAction.OnAddToCartClick)
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
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
        ProductDetailsScreen(
            state = ProductDetailsState(
                productName = "Margherita",
                description = "sample description",
                toppings = (1..20).map {
                    ToppingUi(
                        id = it.toString(),
                        name = "Topping $it",
                        price = "$1.00",
                        imageUrl = ""
                    )
                }
            ),
            onAction = {}
        )
    }
}

@Preview(widthDp = 840, heightDp = 1600)
@Composable
private fun ProductDetailScreenWidthPreview() {
    LazyPizzaTheme {
        ProductDetailsScreen(
            state = ProductDetailsState(
                productName = "Margharita",
                toppings = (1..20).map {
                    ToppingUi(
                        id = it.toString(),
                        name = "Topping $it",
                        price = "$1.00",
                        imageUrl = ""
                    )
                }
            ),
            onAction = {}
        )
    }
}