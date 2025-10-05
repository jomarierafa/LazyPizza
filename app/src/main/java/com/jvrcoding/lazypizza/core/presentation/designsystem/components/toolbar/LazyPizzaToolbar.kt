package com.jvrcoding.lazypizza.core.presentation.designsystem.components.toolbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.PhoneIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.PizzaIcon
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body1Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Bold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyPizzaToolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.body3Bold,
                color = MaterialTheme.colorScheme.textPrimary
            )
        },
        navigationIcon = {
            Icon(
                imageVector = PizzaIcon,
                contentDescription = title,
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp),
            )
        },
        actions = {
            Icon(
                imageVector = PhoneIcon,
                contentDescription = stringResource(R.string.contact),
                tint = MaterialTheme.colorScheme.textSecondary,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "+1 (555) 321-7890",
                style = MaterialTheme.typography.body1Regular,
                color = MaterialTheme.colorScheme.textPrimary
            )
        },
    )
}

@Preview
@Composable
private fun MainToolbarPreview() {
    LazyPizzaTheme {
        LazyPizzaToolbar(
            title = "LazyPizza"
        )
    }
}