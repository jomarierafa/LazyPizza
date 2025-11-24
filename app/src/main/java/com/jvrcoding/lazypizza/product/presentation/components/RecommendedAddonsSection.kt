package com.jvrcoding.lazypizza.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.product.presentation.cart.model.RecommendedProductUi

@Composable
fun RecommendedAddOnsSection(
    label: String,
    products: List<RecommendedProductUi>,
    onAddClick: (RecommendedProductUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = label,
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textSecondary,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {
            items(
                items = products,
                key = { it -> it.id }
            ) { product ->
                AddOnsItems(
                    productName = product.name,
                    imageUrl = product.imageUrl,
                    price = product.price,
                    onAddClick = {
                        onAddClick(product)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecommendedAddOnsSectionPreview() {
    LazyPizzaTheme {
        RecommendedAddOnsSection(
            label = stringResource(R.string.recommended_to_add_to_your_order),
            onAddClick = {},
            products = (1..10).map {
                RecommendedProductUi(
                    id = it.toString(),
                    imageUrl = "",
                    name = "BBQ Sauce",
                    price = "0.59"
                )
            }
        )
    }
}