package com.jvrcoding.lazypizza.product.presentation.checkout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryIconButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.radiobutton.PrimaryRadioButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.SecondaryToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.ChevronDownIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.ChevronUpIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.product.presentation.components.ProductCard
import com.jvrcoding.lazypizza.product.presentation.components.RecommendedAddOnsSection
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenRoot(
    viewModel: CheckoutViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CheckOutScreen(
        state = state
    )
}

@Composable
fun CheckOutScreen(
    state: CheckoutState
) {
    Scaffold(
        topBar = {
            SecondaryToolbar(
                title = stringResource(R.string.order_checkout),
                showBackButton = true,
                onBackClick = {
                    
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                ScheduleSection()
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    expanded = !expanded
                                }
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.order_details),
                            style = MaterialTheme.typography.label2SemiBold,
                            color = MaterialTheme.colorScheme.textSecondary,
                        )
                        PrimaryIconButton(
                            iconTint = MaterialTheme.colorScheme.textSecondary,
                            icon = if (expanded)
                                ChevronUpIcon
                            else
                                ChevronDownIcon,
                            modifier = Modifier.size(22.dp),
                            onClick = {
                                expanded = !expanded
                            }
                        )
                    }
                    AnimatedVisibility(
                        visible = expanded,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        LazyVerticalGrid(
                            userScrollEnabled = false,
                            columns = GridCells.Adaptive(415.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 1000.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(
                                items = state.products,
                                key = { _, it -> it.id }
                            ) { _, product ->
                                ProductCard(
                                    minQuantity = 1,
                                    imageUrl = product.imageUrl,
                                    productName = product.name,
                                    productPrice = product.totalPrice,
                                    description = "",
                                    quantity = product.quantity.toString(),
                                    imageSize = 106.dp,
                                    onAddClick = {
//                                onAction(CartAction.OnIncreaseQuantity(product.id))
                                    },
                                    onMinusClick = {
//                                onAction(CartAction.OnDecreaseQuantity(product.id))
                                    },
                                    onRemoveClick = {
//                                onAction(CartAction.OnRemoveItem(product.id))
                                    },
                                    selected = true,
                                    modifier = Modifier.animateItem()
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

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
                    color = MaterialTheme.colorScheme.outline
                )
            }

        }
    }

}

@Composable
fun ScheduleSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.pickup_time),
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textSecondary
        )
        PrimaryRadioButton(
            text = stringResource(R.string.earliest_available_time),
            selected = true,
            onSelect = {

            }
        )
        PrimaryRadioButton(
            text = stringResource(R.string.schedule_time),
            selected = false,
            onSelect = {

            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "EARLIEST PICKUP TIME:",
                style = MaterialTheme.typography.label2Medium,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Text(
                text = "12:15",
                style = MaterialTheme.typography.label2SemiBold,
                color = MaterialTheme.colorScheme.textPrimary
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview
@Composable
private fun CheckoutScreenPreview() {
    LazyPizzaTheme {
        CheckOutScreen(
            state = CheckoutState()
        )
    }
}