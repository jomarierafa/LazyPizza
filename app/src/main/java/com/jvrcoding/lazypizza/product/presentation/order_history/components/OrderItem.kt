package com.jvrcoding.lazypizza.product.presentation.order_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body4Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label3Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title3
import com.jvrcoding.lazypizza.product.presentation.order_history.models.OrderStatus

@Composable
fun OrderItem(
    orderNo: String,
    date: String,
    items: String,
    amount: String,
    status: OrderStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .dropShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.06f),
                    offset = DpOffset(x = 0.dp, 4.dp)
                )
            )
            .background(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = orderNo,
                style = MaterialTheme.typography.title3,
                color = MaterialTheme.colorScheme.textPrimary
            )
            Text(
                text = date,
                style = MaterialTheme.typography.body4Regular,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = items,
                style = MaterialTheme.typography.body4Regular,
                color = MaterialTheme.colorScheme.textPrimary
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.End,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = status.color
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = status.title.asString(),
                    style = MaterialTheme.typography.label3Medium,
                    color =  Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.total_amount),
                style = MaterialTheme.typography.body4Regular,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.title3,
                color = MaterialTheme.colorScheme.textPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderItemPreview() {
    LazyPizzaTheme {
        OrderItem(
            orderNo = "Order #1234",
            date = "September 25, 12:15",
            items = "1 x Margherita \n2x Pepsi \n2x Cookies Ice Cream",
            amount = "$12.14",
            status = OrderStatus.COMPLETED,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}