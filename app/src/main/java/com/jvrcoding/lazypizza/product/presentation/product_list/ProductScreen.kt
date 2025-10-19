package com.jvrcoding.lazypizza.product.presentation.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield.SearchTextField
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.LazyPizzaToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.product.domain.Product
import com.jvrcoding.lazypizza.product.presentation.product_list.components.OtherProductCard
import com.jvrcoding.lazypizza.product.presentation.product_list.components.PizzaCard
import com.jvrcoding.lazypizza.product.presentation.product_list.components.ProductCategoryRow
import com.jvrcoding.lazypizza.product.presentation.product_list.util.toProduct
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreenRoot(
    onNavigateToProductDetails: (Product) -> Unit,
    viewModel: ProductViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is ProductAction.OnProductClick -> onNavigateToProductDetails(action.product.toProduct())
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ProductScreen(
    state: ProductState,
    onAction: (ProductAction) -> Unit,
) {
    Scaffold(
        topBar = {
            LazyPizzaToolbar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = stringResource(R.string.app_name),
            )
        }
    ) { innerPadding ->

        val context = LocalContext.current
        val gridState = rememberLazyGridState()
        val coroutineScope = rememberCoroutineScope()


        val categoryIndexMap = remember(state.productSections) {
            buildMap {
                var currentIndex = 0
                state.productSections.forEach { (category, products) ->
                    put(category.asString(context), currentIndex)
                    // +1 for stickyHeader
                    currentIndex += 1 + products.size
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
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
                text = state.searchQuery,
                onValueChange = {
                    onAction(ProductAction.OnSearchQueryChange(it))
                },
                hint = stringResource(R.string.search_for_delicious_food)
            )
            ProductCategoryRow(
                categoryList = listOf("Pizza", "Drinks", "Sauces", "Ice Cream"),
                onCategoryClick = { category ->
                    categoryIndexMap[category]?.let { index ->
                        coroutineScope.launch {
                            gridState.animateScrollToItem(index)
                        }
                    }
                }
            )

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(415.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.productSections.forEachIndexed { sectionIndex, (category, products) ->
                    stickyHeader {
                        Text(
                            text = category.asString().uppercase(),
                            style = MaterialTheme.typography.label2SemiBold,
                            color = MaterialTheme.colorScheme.textSecondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.background)
                                .padding(top = 8.dp, bottom = 4.dp)
                        )
                    }
                    itemsIndexed(
                        products,
                        key = { _, product -> product.id}
                    ) { index, product ->
                        val selectedItem = state.selectedProducts.find { it.productId == product.id }
                        if(product.type == "Pizza") {
                            PizzaCard(
                                imageUrl = product.imageUrl,
                                productName = product.name,
                                productPrice = product.price,
                                productDescription = product.description,
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            onAction(ProductAction.OnProductClick(product))
                                        }
                                    )
                            )
                        } else {
                            OtherProductCard(
                                imageUrl = product.imageUrl,
                                productName = product.name,
                                productPrice = product.price,
                                quantity = "${selectedItem?.quantity}",
                                onAddToCardClick = {
                                    onAction(ProductAction.OnAddToCardClick(product.id))
                                },
                                onAddClick = {
                                    onAction(ProductAction.OnAddClick(product.id))
                                },
                                onMinusClick = {
                                    onAction(ProductAction.OnMinusClick(product.id))
                                },
                                onRemoveClick = {
                                    onAction(ProductAction.OnRemoveClick(product.id))
                                },
                                selected = selectedItem != null
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
            state = ProductState(),
            onAction = {}
        )
    }
}