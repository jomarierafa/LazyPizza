package com.jvrcoding.lazypizza.product.presentation.order_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.components.EmptyStateScreen
import com.jvrcoding.lazypizza.product.presentation.order_history.components.OrderItem
import com.jvrcoding.lazypizza.product.presentation.order_history.models.OrderStatus
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderHistoryScreenRoot(
    onNavigateToAuthentication: () -> Unit,
    viewModel: OrderHistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    OrderHistoryScreen(
        state = state,
        onAction = {
            when(it) {
                OrderHistoryAction.OnSignInClick -> onNavigateToAuthentication()
            }
        }
    )
}

@Composable
fun OrderHistoryScreen(
    state: OrderHistoryState,
    onAction: (OrderHistoryAction) -> Unit,
) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                title = stringResource(R.string.order_history),
                showBackButton = false
            )
        }
    ) { innerPadding ->
        if(!state.isUserSignedIn) {
            EmptyStateScreen(
                modifier = Modifier.padding(innerPadding),
                title = stringResource(R.string.not_signed_in),
                subtitle = stringResource(R.string.please_sign_in_to_view_your_order_history),
                buttonText = stringResource(R.string.sign_in),
                onButtonClick = {
                    onAction(OrderHistoryAction.OnSignInClick)
                }
            )
        } else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxSize(),
                columns = StaggeredGridCells.Adaptive(415.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(10) {
                    OrderItem(
                        orderNo = "Order $it",
                        date = "September 25, 12:15",
                        items = "1 x Margherita \n2x Pepsi \n2x Cookies Ice Cream",
                        amount = "$12.14",
                        status = OrderStatus.IN_PROGRESS,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OrderHistoryScreenPreview() {
    LazyPizzaTheme {
        OrderHistoryScreen(
            state = OrderHistoryState(
                isUserSignedIn = true
            ),
            onAction = {}
        )
    }
}