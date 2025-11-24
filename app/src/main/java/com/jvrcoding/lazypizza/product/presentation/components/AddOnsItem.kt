package com.jvrcoding.lazypizza.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryIconButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.PlusIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body1Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1SemiBold

@Composable
fun AddOnsItems(
    productName: String,
    imageUrl: String,
    price: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val isInPreview = LocalInspectionMode.current

    Column(
        modifier =  modifier
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.06f),
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh,)
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(if(isInPreview) R.drawable.logo else imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = productName,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
        )
        Text(
            text = productName,
            style = MaterialTheme.typography.body1Regular,
            color = MaterialTheme.colorScheme.textSecondary,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$$price",
                style = MaterialTheme.typography.title1SemiBold,
                color = MaterialTheme.colorScheme.textPrimary
            )
            PrimaryIconButton(
                icon = PlusIcon,
                iconTint = MaterialTheme.colorScheme.primary,
                onClick = onAddClick,
                modifier = Modifier.size(22.dp)
            )
        }
    }

}

@Preview
@Composable
private fun AddOnsItemPreview() {
    LazyPizzaTheme {
        AddOnsItems(
            productName = "BBQ Sauce",
            imageUrl = "",
            price = "12.14",
            onAddClick = {}
        )
    }
}