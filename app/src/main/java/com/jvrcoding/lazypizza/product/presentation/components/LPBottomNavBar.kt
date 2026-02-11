package com.jvrcoding.lazypizza.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.product.presentation.model.Tab

@Composable
fun LPBottomNavBar(
    onItemClick: (Tab) -> Unit,
    selectedTab: Tab,
    cartItemCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 16.dp,
                    spread = 0.dp,
                    color = MaterialTheme.colorScheme.textPrimary.copy(alpha = 0.06f),
                    offset = DpOffset(x = 0.dp, (-4).dp)
                )
            )
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Tab.entries.forEach { item ->
            LPNavigationItem(
                icon = item.icon,
                label = item.label,
                selected = selectedTab == item,
                onClick = { onItemClick(item) },
                badgeCount = if(item == Tab.CART) cartItemCount else 0,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(96.dp)
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun LPBottomNavBarPreview() {
    LazyPizzaTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            LPBottomNavBar(
                onItemClick = {},
                selectedTab = Tab.MENU,
                cartItemCount = 5,
                modifier = Modifier.fillMaxWidth().align(alignment = Alignment.BottomCenter)
            )
        }

    }
}