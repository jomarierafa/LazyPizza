package com.jvrcoding.lazypizza.product.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryIconButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.SecondaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.BackGround
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.TrashIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body1Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body4Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1SemiBold

@Composable
fun ProductCard(
    imageUrl: String,
    productName: String,
    productPrice: String,
    quantity: String,
    onMinusClick: () -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    description: String? = null,
    onAddToCardClick: () -> Unit = {},
    imageSize: Dp = 120.dp,
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
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if(isInPreview) R.drawable.logo else imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = productName,
                    modifier = Modifier.size(imageSize)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Text(
                        text = productName,
                        style = MaterialTheme.typography.body1Medium,
                        color = MaterialTheme.colorScheme.textPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    if(selected) {
                        PrimaryIconButton(
                            icon = TrashIcon,
                            iconTint = MaterialTheme.colorScheme.primary,
                            onClick = onRemoveClick,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                description?.let {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body3Regular,
                        color = MaterialTheme.colorScheme.textSecondary,
                        modifier = Modifier.weight(1f)
                    )
                }
                if(!selected) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = productPrice,
                            style = MaterialTheme.typography.title1SemiBold,
                            color = MaterialTheme.colorScheme.textPrimary
                        )
                        SecondaryButton(
                            text = stringResource(R.string.add_to_cart),
                            onClick = onAddToCardClick,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        QuantitySelector(
                            quantity = quantity,
                            onMinusClick = onMinusClick,
                            onAddClick = onAddClick,
                            modifier = Modifier.width(96.dp)
                        )
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = totalPrice(quantity, productPrice),
                                style = MaterialTheme.typography.title1SemiBold,
                                color = MaterialTheme.colorScheme.textPrimary
                            )
                            Text(
                                text = "$quantity x $productPrice",
                                style = MaterialTheme.typography.body4Regular,
                                color = MaterialTheme.colorScheme.textSecondary
                            )

                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun totalPrice(quantity: String, price: String): String {
    val quantityDouble = quantity.toDouble()
    val priceDouble = price.replace("$", "").toDouble()
    val totalPrice = quantityDouble * priceDouble
    return "$${String.format("%.2f", totalPrice)}"
}

@Preview(showBackground = true)
@Composable
private fun OtherProductCardPreview() {
    LazyPizzaTheme {
        ProductCard(
            imageUrl = "https://res.cloudinary.com/drtxnnmwo/image/upload/v1759416011/spinach_hsus2b.png",
            productName = "Margherita",
            productPrice = "$10.50",
            description = "1 x Extra Cheese\n" +
                    "1 x Pepperoni\n wadawawdawd",
            selected = true,
            quantity = "1",
            onAddToCardClick = {},
            onMinusClick = {},
            onAddClick = {},
            onRemoveClick = {},
            modifier = Modifier.background(BackGround).padding(16.dp)
        )
    }
}