package com.jvrcoding.lazypizza.product.presentation.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.jvrcoding.lazypizza.product.presentation.product_details.models.ToppingUi

@Composable
fun ToppingSection(
    toppingList: List<ToppingUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.add_extra_toppings),
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textSecondary
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(toppingList.size) { index ->
                ToppingCard(
                    imageUrl = toppingList[index].imageUrl,
                    toppingName = toppingList[index].name,
                    toppingPrice = toppingList[index].price,
                    selected = false
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
                    description = "Description $it",
                    price = "$ $it.50",
                    imageUrl = ""
                )
            }
        )
    }
}