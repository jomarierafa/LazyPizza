package com.jvrcoding.lazypizza.product.presentation.product_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1SemiBold

@Composable
fun TopSection(
    imageUrl: String,
    productName: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .clip(RoundedCornerShape(bottomEnd = 16.dp))
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(if(isInPreview) R.drawable.logo else imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = productName,
                modifier = Modifier.size(240.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .clip(RoundedCornerShape(topStart = 16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp)
        ) {
            Text(
                text = productName,
                style = MaterialTheme.typography.title1SemiBold,
                color = MaterialTheme.colorScheme.textPrimary,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.body3Regular,
                color = MaterialTheme.colorScheme.textSecondary,
            )
        }
    }



}

@Preview
@Composable
private fun TopSectionPreview() {
    LazyPizzaTheme {
        TopSection(
            imageUrl = "",
            productName = "Margharita",
            description = "Tomato sauce, Mozzarella, Fresh basil, Olive oil"
        )
    }
}