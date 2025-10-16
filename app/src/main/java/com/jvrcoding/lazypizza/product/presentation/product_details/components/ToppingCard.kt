package com.jvrcoding.lazypizza.product.presentation.product_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.BackGround
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title2
import com.jvrcoding.lazypizza.product.presentation.components.QuantitySelector

@Composable
fun ToppingCard(
    imageUrl: String,
    toppingName: String,
    toppingPrice: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current
    Card(
        modifier = modifier
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = if(selected) 6.dp else 16.dp,
                    spread = 0.dp,
                    color = if(selected)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else
                        MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.04f),
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outline
        ),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if (isInPreview) R.drawable.topping_preview else imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = toppingName,
                    modifier = Modifier
                        .size(54.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = toppingName,
                style = MaterialTheme.typography.body3Regular,
                color = MaterialTheme.colorScheme.textSecondary,
            )
            if(selected) {
                QuantitySelector(
                    quantity = "1",
                    onAddClick = {},
                    onMinusClick = {}
                )
            } else {
                Text(
                    text = toppingPrice,
                    style = MaterialTheme.typography.title2,
                    color = MaterialTheme.colorScheme.textPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToppingCardPreview() {
    LazyPizzaTheme {
        ToppingCard(
            imageUrl = "",
            toppingName = "Bacon",
            toppingPrice = "$1",
            selected = false,
            modifier = Modifier
                .background(BackGround)
                .width(150.dp)
                .padding(16.dp)
        )
    }
}