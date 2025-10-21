package com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.ArrowLeftIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body1Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1Medium,
                    color = MaterialTheme.colorScheme.textPrimary
                )
            }
        },
        navigationIcon = {
            if(showBackButton) {
                FilledIconButton(
                    onClick = onBackClick,
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.textSecondary8,
                    )
                ) {
                    Icon(
                        imageVector = ArrowLeftIcon,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun SecondaryToolbarPreview() {
    LazyPizzaTheme {
        SecondaryToolbar(
            onBackClick = {},
            title = "Cart",
            showBackButton = false
        )
    }
}