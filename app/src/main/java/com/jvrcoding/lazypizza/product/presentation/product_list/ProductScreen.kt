package com.jvrcoding.lazypizza.product.presentation.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield.SearchTextField
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.LazyPizzaToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.product_list.components.OtherProductCard
import com.jvrcoding.lazypizza.product.presentation.product_list.components.PizzaCard
import com.jvrcoding.lazypizza.product.presentation.product_list.components.ProductCategoryRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductScreen(
        state = state,
    )
}

@Composable
fun ProductScreen(
    state: ProductState,
) {
    Scaffold(
        topBar = {
            LazyPizzaToolbar(
                title = stringResource(R.string.app_name),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.banner),
                contentDescription = stringResource(R.string.banner),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(150.dp)
            )
            SearchTextField(
                state = TextFieldState(),
                hint = stringResource(R.string.search_for_delicious_food)
            )
            ProductCategoryRow(
                categoryList = listOf("Pizza", "Drinks", "Sauces", "Ice Cream")
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.productSections.forEachIndexed { sectionIndex, (category, products) ->
                    stickyHeader {
                        Text(category.asString())
                    }
                    itemsIndexed(
                        products,
                        key = { _, product -> product.id}
                    ) { index, product ->
                        if(product.type == "Pizza") {
                            PizzaCard(
                                imageUrl = product.imageUrl,
                                productName = product.name,
                                productPrice = product.price,
                                productDescription = product.description
                            )
                        } else {
                            OtherProductCard(
                                imageUrl = product.imageUrl,
                                productName = product.name,
                                productPrice = product.price,
                                quantity = "2",
                                onAddToCardClick = {},
                                onAddClick = {},
                                onMinusClick = {},
                                onRemoveClick = {},
                                modifyProduct = false
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview
//@PreviewScreenSizes
@Composable
private fun ProductsScreenPreview() {
    LazyPizzaTheme {
        ProductScreen(
            state = ProductState()
        )
    }
}