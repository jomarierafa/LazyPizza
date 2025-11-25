package com.jvrcoding.lazypizza.product.presentation.checkout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.radiobutton.PrimaryRadioButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@Composable
fun AdaptiveScheduleSection(
    mobileLayout: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.pickup_time),
            style = MaterialTheme.typography.label2SemiBold,
            color = MaterialTheme.colorScheme.textSecondary
        )
        RadioButtonGroup(
            mobileLayout = mobileLayout,
            modifier = Modifier
                .selectableGroup()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "EARLIEST PICKUP TIME:",
                style = MaterialTheme.typography.label2Medium,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Text(
                text = "12:15",
                style = MaterialTheme.typography.label2SemiBold,
                color = MaterialTheme.colorScheme.textPrimary
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun RadioButtonGroup(
    mobileLayout: Boolean,
    modifier: Modifier = Modifier
) {
    if(mobileLayout) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryRadioButton(
                text = stringResource(R.string.earliest_available_time),
                selected = true,
                modifier = modifier.fillMaxWidth(),
                onSelect = {

                }
            )
            PrimaryRadioButton(
                text = stringResource(R.string.schedule_time),
                selected = false,
                modifier = modifier.fillMaxWidth(),
                onSelect = {

                }
            )
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryRadioButton(
                text = stringResource(R.string.earliest_available_time),
                selected = true,
                modifier = modifier.weight(1f),
                onSelect = {

                },
            )
            PrimaryRadioButton(
                text = stringResource(R.string.schedule_time),
                selected = false,
                modifier = modifier.weight(1f),
                onSelect = {

                }
            )
        }
    }
}