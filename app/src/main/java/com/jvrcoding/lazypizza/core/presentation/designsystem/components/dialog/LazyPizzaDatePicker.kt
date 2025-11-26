package com.jvrcoding.lazypizza.core.presentation.designsystem.components.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.TextButton
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun LazyPizzaDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val todayMillis = remember {
        LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        }
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            PrimaryButton(
                text = stringResource(R.string.ok),
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(datePickerState.selectedDateMillis!!)
                        onDismiss()
                    }
                },
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        dismissButton = {
            TextButton(
                text = stringResource(R.string.cancel),
                onClick = onDismiss
            )
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            dateFormatter = DatePickerDefaults.dateFormatter(
                selectedDateSkeleton = "MMMM d"
            )
        )
    }
}