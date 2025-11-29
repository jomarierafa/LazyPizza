package com.jvrcoding.lazypizza.product.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label1Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label1SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@Composable
fun AdaptiveBottomSection(
    isExpandedLayout: Boolean,
    totalOrder: String,
    isPlacingOrder: Boolean,
    onPlaceOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(!isExpandedLayout) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OrderTotal(
                total = totalOrder,
                modifier = Modifier
                    .fillMaxWidth()
            )
            PrimaryButton(
                text = stringResource(R.string.place_order),
                onClick = onPlaceOrderClick,
                isLoading = isPlacingOrder,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderTotal(
                total = totalOrder,
                modifier = Modifier
            )
            PrimaryButton(
                text = stringResource(R.string.place_order),
                onClick = onPlaceOrderClick,
                modifier = Modifier
                    .width(380.dp)
            )
        }
    }

}

@Composable
fun OrderTotal(
    total: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier  = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.order_total),
            style = MaterialTheme.typography.label1Medium,
            color = MaterialTheme.colorScheme.textSecondary,
        )
        Text(
            text = total,
            style = MaterialTheme.typography.label1SemiBold,
            color = MaterialTheme.colorScheme.textPrimary,
        )
    }
}