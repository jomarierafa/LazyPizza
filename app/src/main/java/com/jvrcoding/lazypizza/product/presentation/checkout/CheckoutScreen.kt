package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.checkout.components.AdaptiveBottomSection
import com.jvrcoding.lazypizza.product.presentation.checkout.components.AdaptiveScheduleSection
import com.jvrcoding.lazypizza.product.presentation.checkout.components.CommentSection
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.dialog.LazyPizzaDatePicker
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.dialog.LazyPizzaTimePicker
import com.jvrcoding.lazypizza.product.presentation.checkout.components.OrderDetailsSection
import com.jvrcoding.lazypizza.product.presentation.components.RecommendedAddOnsSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenRoot(
    onBackClick: () -> Unit,
    viewModel: CheckoutViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CheckOutScreen(
        state = state,
        onAction = { action ->
            when(action) {
                CheckoutAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun CheckOutScreen(
    state: CheckoutState,
    onAction: (CheckoutAction) -> Unit,
) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                title = stringResource(R.string.order_checkout),
                showBackButton = true,
                onBackClick = {
                    onAction(CheckoutAction.OnBackClick)
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 64.dp)
            ) {
                item {
                    AdaptiveScheduleSection(
                        selectedOption = state.selectedOption,
                        onPickupTimeSelected = {
                            onAction(CheckoutAction.OnPickupTimeSelected(it))
                        },
                        mobileLayout = true,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    )
                }
                item {
                    OrderDetailsSection(
                        products = state.products,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                item {
                    RecommendedAddOnsSection(
                        label = stringResource(R.string.recommended_add_ons),
                        products = state.recommendedProducts,
                        onAddClick = {
                        }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    CommentSection(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

            }
            
           AdaptiveBottomSection(
               totalOrder = "$55.67",
               mobileLayout = true,
               onPlaceOrderClick = {},
               modifier = Modifier
                   .background(MaterialTheme.colorScheme.background)
                   .padding(horizontal = 16.dp)
                   .padding(vertical = 16.dp)
                   .fillMaxWidth()
                   .align(Alignment.BottomCenter)
           )

        }

        if(state.showDatePicker) {
            LazyPizzaDatePicker(
                onDateSelected = {
                    onAction(CheckoutAction.OnDateSelected(it))
                },
                onDismiss = {
                    onAction(CheckoutAction.OnDismissDatePicker)
                }
            )
        }

        if(state.showTimePicker) {
            LazyPizzaTimePicker(
                onDismiss = {
                    onAction(CheckoutAction.OnDismissTimePicker)
                }
            )
        }

    }
}

@Preview
@Composable
private fun CheckoutScreenPreview() {
    LazyPizzaTheme {
        CheckOutScreen(
            state = CheckoutState(),
            onAction = {}
        )
    }
}