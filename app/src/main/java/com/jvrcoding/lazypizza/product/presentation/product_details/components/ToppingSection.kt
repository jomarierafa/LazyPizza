package com.jvrcoding.lazypizza.product.presentation.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.util.fadingEdge
import com.jvrcoding.lazypizza.product.presentation.product_details.ProductDetailsAction
import com.jvrcoding.lazypizza.product.presentation.product_details.models.SelectedTopping
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

@Composable
fun ToppingSection(
    toppingList: List<ToppingUi>,
    onAction: (ProductDetailsAction) -> Unit,
    selectedTopping: List<SelectedTopping>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fadingEdge(500f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.add_extra_toppings),
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textSecondary
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 98.dp
            )
        ) {
            itemsIndexed(
                items = toppingList,
                key = { _, it -> it.id }
            ) { _, topping ->
                val selectedItem = selectedTopping.find { it.id == topping.id }
                ToppingCard(
                    imageUrl = topping.imageUrl,
                    toppingName = topping.name,
                    toppingPrice = topping.price,
                    selected = selectedItem != null,
                    quantity = selectedItem?.quantity ?: 0,
                    modifier = Modifier.clickable(
                        onClick = {
                            onAction(ProductDetailsAction.OnToppingSelect(topping.id))
                        }
                    ),
                    onAddQuantity = {
                        onAction(ProductDetailsAction.OnAddQuantity(topping.id))
                    },
                    onReduceQuantity = {
                        onAction(ProductDetailsAction.OnReduceQuantity(topping.id))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToppingScreenPreview() {
    LazyPizzaTheme {
        ToppingSection(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh),
            toppingList = (1..20).map {
                ToppingUi(
                    id = it.toString(),
                    name = "Topping $it",
                    price = "$ $it.50",
                    imageUrl = ""
                )
            },
            selectedTopping = listOf(),
            onAction = {}
        )
    }
}