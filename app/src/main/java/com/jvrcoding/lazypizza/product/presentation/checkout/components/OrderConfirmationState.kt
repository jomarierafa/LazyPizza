package com.jvrcoding.lazypizza.product.presentation.checkout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.TextButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1Medium
import java.util.Calendar

@Composable
fun OrderConfirmationState(
    orderNo: String,
    pickupTime: String,
    onBackToMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(R.string.your_order_has_been_placed),
            style = MaterialTheme.typography.title1Medium,
            color = MaterialTheme.colorScheme.textPrimary
        )
        Text(
            text = stringResource(R.string.thank_you_for_your_order_please_come_at_the_indicated_time),
            style = MaterialTheme.typography.body3Regular,
            color = MaterialTheme.colorScheme.textSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OrderDetail(
                description = stringResource(R.string.order_number),
                value = orderNo
            )
            OrderDetail(
                description = stringResource(R.string.pickup_time),
                value = pickupTime
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            text = stringResource(R.string.back_to_menu),
            onClick = onBackToMenuClick,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetail(
    description: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.label2Medium,
            color = MaterialTheme.colorScheme.textSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderConfirmationStatePreview() {
    LazyPizzaTheme {
        OrderConfirmationState(
            orderNo = "#12345",
            pickupTime = "SEPTEMBER 25, 12:15",
            onBackToMenuClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}