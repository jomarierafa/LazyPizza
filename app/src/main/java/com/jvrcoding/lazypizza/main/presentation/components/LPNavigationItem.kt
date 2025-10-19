package com.jvrcoding.lazypizza.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.primary8
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textOnPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title4

@Composable
fun LPNavigationItem(
    icon: Int,
    label: String,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    badgeCount: Int = 0
) {

    Column(
        modifier = modifier
            .clickable(
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            FilledIconButton(
                modifier = Modifier.size(28.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor =   if(selected) {
                        MaterialTheme.colorScheme.primary8
                    } else {
                        Color.Transparent
                    },
                    contentColor = if(selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                ),
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = label,
                    modifier = Modifier.size(16.dp)
                )
            }

            if (badgeCount > 0) {
                Text(
                    text = badgeCount.toString(),
                    color = MaterialTheme.colorScheme.textOnPrimary,
                    style = MaterialTheme.typography.title4,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-6).dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.title4,
            color = if(selected) {
                MaterialTheme.colorScheme.textPrimary
            } else {
                MaterialTheme.colorScheme.textSecondary
            }
        )
    }
}

@Preview
@Composable
private fun LazyPizzaNavigationItemPreview() {
    LazyPizzaTheme {
        LPNavigationItem(
            icon = R.drawable.ic_trash,
            label = "Menu",
            selected = true,
            onClick = {},
            badgeCount = 5,
            modifier = Modifier
                .width(96.dp)
                .padding(vertical = 10.dp)
        )
    }
}