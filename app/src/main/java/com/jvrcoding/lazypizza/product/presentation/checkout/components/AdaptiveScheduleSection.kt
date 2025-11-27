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
import com.jvrcoding.lazypizza.product.presentation.checkout.models.PickupTime

@Composable
fun AdaptiveScheduleSection(
    selectedOption: PickupTime,
    onPickupTimeSelected: (PickupTime) -> Unit,
    mobileLayout: Boolean,
    pickupTime: String,
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
            selectedOption = selectedOption,
            onOptionSelected = onPickupTimeSelected,
            modifier = Modifier
                .selectableGroup()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.earliest_pickup_time),
                style = MaterialTheme.typography.label2Medium,
                color = MaterialTheme.colorScheme.textSecondary
            )
            Text(
                text = pickupTime,
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
    onOptionSelected: (PickupTime) -> Unit,
    selectedOption: PickupTime,
    modifier: Modifier = Modifier
) {
    if(mobileLayout) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PickupTime.entries.forEach { pickupTime ->
                PrimaryRadioButton(
                    text = pickupTime.value.asString(),
                    selected = (pickupTime == selectedOption),
                    modifier = modifier.fillMaxWidth(),
                    onSelect = {
                        onOptionSelected(pickupTime)
                    }
                )
            }
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