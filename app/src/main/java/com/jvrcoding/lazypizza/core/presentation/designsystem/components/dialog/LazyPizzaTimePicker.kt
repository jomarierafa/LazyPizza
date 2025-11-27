package com.jvrcoding.lazypizza.core.presentation.designsystem.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.TextButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.label2SemiBold
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.util.UiText
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyPizzaTimePicker(
    onDismiss: () -> Unit,
    onOkButtonClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: UiText? = null,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .width(264.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.select_time),
                style = MaterialTheme.typography.label2SemiBold,
                color = MaterialTheme.colorScheme.textSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TimeInput(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )
            errorMessage?.let {
                Text(
                    text = it.asString(),
                    style = MaterialTheme.typography.label2Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss
                )
                PrimaryButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        onOkButtonClick(timePickerState.hour, timePickerState.minute)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun LazyPizzaTimePickerPreview() {
    LazyPizzaTheme {
        LazyPizzaTimePicker(
            onOkButtonClick = { _, _ ->},
            onDismiss = {}
        )
    }
}