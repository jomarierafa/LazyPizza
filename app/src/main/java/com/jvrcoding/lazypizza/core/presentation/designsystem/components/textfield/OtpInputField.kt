package com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield

import android.view.KeyEvent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body2Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary

@Composable
fun OtpInputField(
    number: Int?,
    focusRequester: FocusRequester,
    isError: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text by remember(number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if (number != null) 1 else 0
                )
            )
        )
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            val newNumber = newText.text
            if(newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                onNumberChanged(newNumber.toIntOrNull())
            }
        },
        placeholder = {
            if(!isFocused) {
                Text(
                    text = "0",
                    style = MaterialTheme.typography.body2Regular,
                    color = MaterialTheme.colorScheme.textSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        isError = isError,
        interactionSource = interactionSource,
        singleLine = true,
        shape = CircleShape,
        textStyle = MaterialTheme.typography.body2Regular.copy(
            color = MaterialTheme.colorScheme.textPrimary,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.outline,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.primary,
            errorSupportingTextColor =  MaterialTheme.colorScheme.error,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            errorContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            cursorColor = MaterialTheme.colorScheme.textPrimary
        ),
        modifier = modifier
//            .padding(10.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { event ->
                val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                if (didPressDelete && number == null) {
                    onKeyboardBack()
                }
                false
            },
    )
}

