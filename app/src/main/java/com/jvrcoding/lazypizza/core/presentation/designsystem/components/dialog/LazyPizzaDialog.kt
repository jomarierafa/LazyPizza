package com.jvrcoding.lazypizza.core.presentation.designsystem.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.TextButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1Medium

@Composable
fun LazyPizzaDialog(
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.title1Medium,
                color = MaterialTheme.colorScheme.textPrimary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    text = negativeButtonText,
                    onClick = onNegativeButtonClick,
                )
                Spacer(modifier = Modifier.width(8.dp))
                PrimaryButton(
                    text = positiveButtonText,
                    onClick = onPositiveButtonClick
                )
            }
        }

    }

}

@Preview
@Composable
private fun LazyPizzaDialogPreview() {
    LazyPizzaTheme {
        LazyPizzaDialog(
            message = stringResource(R.string.logout_confirmation_message),
            onDismiss = {},
            negativeButtonText = stringResource(R.string.cancel),
            positiveButtonText = stringResource(R.string.log_out),
            onNegativeButtonClick = {},
            onPositiveButtonClick = {}
        )
    }
}