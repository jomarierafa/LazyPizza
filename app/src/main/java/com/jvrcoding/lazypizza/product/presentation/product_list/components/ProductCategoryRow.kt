package com.jvrcoding.lazypizza.product.presentation.product_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary

@Composable
fun ProductCategoryRow(
    categoryList: List<String>,
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        categoryList.forEach { category ->
            SuggestionChip(
                modifier = Modifier.height(32.dp),
                onClick = { onCategoryClick(category) },
                label = {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.body3Medium,
                        color = MaterialTheme.colorScheme.textPrimary,
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
            )
        }
    }
}

@Preview
@Composable
private fun ProductCategoryRowPreview() {
    LazyPizzaTheme {
        ProductCategoryRow(
            categoryList = listOf("Pizza", "Drinks", "Sauces", "Ice Cream"),
            onCategoryClick = {},
        )
    }
}