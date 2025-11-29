package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import com.jvrcoding.lazypizza.core.presentation.util.DeviceConfiguration
import com.jvrcoding.lazypizza.product.presentation.checkout.components.OrderDetailsSection
import com.jvrcoding.lazypizza.product.presentation.checkout.components.TransactionSummary
import com.jvrcoding.lazypizza.product.presentation.components.RecommendedAddOnsSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenRoot(
    onBackClick: () -> Unit,
    onBackToMenuClick: () -> Unit,
    viewModel: CheckoutViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CheckOutScreen(
        state = state,
        onAction = { action ->
            when(action) {
                CheckoutAction.OnBackClick -> onBackClick()
                CheckoutAction.OnBackToMenuClick -> onBackToMenuClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun CheckOutScreen(
    state: CheckoutState,
    onAction: (CheckoutAction) -> Unit,
    isExpanded: Boolean? = null,
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
        val isExpandedLayout = isExpanded ?: run {
            val activity = LocalActivity.current as ComponentActivity
            val windowSizeClass = calculateWindowSizeClass(activity = activity)
            DeviceConfiguration.isExpandedLayout(windowSizeClass)
        }


        BoxWithConstraints(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            val parentWidth = this.maxWidth
            val parentHeight = this.maxHeight
            if(state.showTransactionSummary) {
                TransactionSummary(
                    orderNo = state.orderNo,
                    pickupTime = state.pickupTime,
                    onBackToMenuClick = {
                        onAction(CheckoutAction.OnBackToMenuClick)
                    },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .widthIn(max = 400.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = parentHeight * 0.18f)
                )

            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 124.dp)
                ) {
                    item(
                        key = "scheduleSection"
                    ) {
                        AdaptiveScheduleSection(
                            selectedOption = state.selectedOption,
                            onPickupTimeSelected = {
                                onAction(CheckoutAction.OnPickupTimeSelected(it))
                            },
                            isExpandedLayout = isExpandedLayout,
                            pickupTime = state.pickupTime,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                        )
                    }
                    item(
                        key = "orderDetailsSection"
                    ) {
                        OrderDetailsSection(
                            products = state.products,
                            onAction = onAction,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }
                    item(
                        key = "recommendedAddOnsSection"
                    ) {
                        RecommendedAddOnsSection(
                            label = stringResource(R.string.recommended_add_ons),
                            products = state.recommendedProducts,
                            onAddClick = {
                                onAction(CheckoutAction.OnAddProduct(it))
                            }
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    item(
                        key = "commentSection"
                    ) {
                        CommentSection(
                            value = state.comment,
                            onValueChange = {
                                onAction(CheckoutAction.OnCommentChange(it))
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                }

                AdaptiveBottomSection(
                    totalOrder = "$${state.totalPrice}",
                    mobileLayout = true,
                    onPlaceOrderClick = {
                        onAction(CheckoutAction.OnPlaceOrderClick)
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )

            }
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
                errorMessage = state.timePickerErrorMessage,
                onDismiss = {
                    onAction(CheckoutAction.OnDismissTimePicker)
                },
                onOkButtonClick = { hour, minute ->
                    onAction(CheckoutAction.TimeSelected(hour, minute))
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
            onAction = {},
            isExpanded = false
        )
    }
}