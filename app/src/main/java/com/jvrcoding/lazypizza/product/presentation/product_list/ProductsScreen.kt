package com.jvrcoding.lazypizza.product.presentation.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield.SearchTextField
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar.LazyPizzaToolbar
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.product.presentation.product_list.components.ProductCategoryRow

@Composable
fun ProductsScreenRoot(modifier: Modifier = Modifier) {

}

@Composable
fun ProductsScreen() {
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
                    .height(155.dp)
            )
            SearchTextField(
                state = TextFieldState(),
                hint = stringResource(R.string.search_for_delicious_food)
            )
            ProductCategoryRow(
                categoryList = listOf("Pizza", "Drinks", "Sauces", "Ice Cream")
            )
        }
    }
}

@Preview()
@Composable
private fun ProductsScreenPreview() {
    LazyPizzaTheme {
        ProductsScreen()
    }
}