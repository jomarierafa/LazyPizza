package com.jvrcoding.lazypizza.product.presentation.checkout.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryIconButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.ChevronDownIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.ChevronUpIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.product.presentation.cart.model.CartProductUi
import com.jvrcoding.lazypizza.product.presentation.checkout.CheckoutAction
import com.jvrcoding.lazypizza.product.presentation.components.ProductCard

@Composable
fun OrderDetailsSection(
    onAction: (CheckoutAction) -> Unit,
    products: List<CartProductUi>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
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
                    items = products,
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
                                onAction(CheckoutAction.OnIncreaseQuantity(product.id))
                        },
                        onMinusClick = {
                                onAction(CheckoutAction.OnDecreaseQuantity(product.id))
                        },
                        onRemoveClick = {
                                onAction(CheckoutAction.OnRemoveItem(product.id))
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