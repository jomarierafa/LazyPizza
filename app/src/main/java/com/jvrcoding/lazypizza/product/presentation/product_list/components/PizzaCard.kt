package com.jvrcoding.lazypizza.product.presentation.product_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.BackGround
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body1Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1SemiBold

@Composable
fun PizzaCard(
    imageUrl: String,
    productName: String,
    productDescription: String,
    productPrice: String,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        modifier = modifier
            .height(IntrinsicSize.Max)
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.06f),
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(if(isInPreview) R.drawable.logo else imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = productName,
                modifier = Modifier
                    .size(120.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.body1Medium,
                    color = MaterialTheme.colorScheme.textPrimary,
                )
                Text(
                    text = productDescription,
                    style = MaterialTheme.typography.body3Regular,
                    color = MaterialTheme.colorScheme.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = productPrice,
                    style = MaterialTheme.typography.title1SemiBold,
                    color = MaterialTheme.colorScheme.textPrimary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    LazyPizzaTheme {
        PizzaCard(
            imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416011/spinach_hsus2b.png",
            productName = "Margherita",
            productDescription = "Tomato sauce, mozzarella, fresh basil, olive oil",
            productPrice = "$10.50",
            modifier = Modifier.background(BackGround).padding(16.dp)
        )
    }
}