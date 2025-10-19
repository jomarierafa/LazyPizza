package com.jvrcoding.lazypizza.main.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.main.presentation.navigation.Tab

@Composable
fun LPNavigationRail(
    onItemClick: (Tab) -> Unit,
    selectedTab: Tab,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            )
    ) {
        Spacer(Modifier.weight(1f))
        Tab.entries.forEach { item ->
            LPNavigationItem(
                icon = item.icon,
                label = item.label,
                selected = selectedTab == item,
                onClick = { onItemClick(item) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(54.dp)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LPNavigationRailPreview() {
    LazyPizzaTheme {
        LPNavigationRail(
            onItemClick = {},
            selectedTab = Tab.History
        )
    }
}