package com.jvrcoding.lazypizza.auth.presentation.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jvrcoding.lazypizza.R
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.PrimaryButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.button.TextButton
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield.OtpInputField
import com.jvrcoding.lazypizza.core.presentation.designsystem.components.textfield.PrimaryTextField
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.LazyPizzaTheme
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body3Regular
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.body4Medium
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textPrimary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.textSecondary
import com.jvrcoding.lazypizza.core.presentation.designsystem.theme.title1Medium
import com.jvrcoding.lazypizza.core.presentation.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthenticationScreenRoot(
    onNavigateToMenuScreen: () -> Unit,
    viewModel: AuthenticationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            AuthenticationEvent.SuccessfulAuthentication -> onNavigateToMenuScreen()
        }

    }
    AuthenticationScreen(
        state  = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AuthenticationScreen(
    state: AuthenticationState,
    onAction: (AuthenticationAction) -> Unit,
) {
    val focusRequesters = remember {
        List(6) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current
    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if(allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        val parentWidth = this.maxWidth
        val parentHeight = this.maxHeight
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 400.dp)
                .padding(top = parentHeight * 0.2f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.welcome_to_lazypizza),
                style = MaterialTheme.typography.title1Medium,
                color = MaterialTheme.colorScheme.textPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = state.subtitleText.asString(),
                style = MaterialTheme.typography.body3Regular,
                color = MaterialTheme.colorScheme.textSecondary,
            )
            Spacer(Modifier.height(16.dp))
            PrimaryTextField(
                value = state.phoneNumber,
                onValueChange = {
                    onAction(AuthenticationAction.OnPhoneNumberChange(it))
                },
                placeholder = stringResource(R.string.phone_number_placeholder),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                )
            )

            AnimatedVisibility(
                visible = state.isVerificationPhase,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    state.code.forEachIndexed { index, number ->
                        OtpInputField(
                            number = number,
                            focusRequester = focusRequesters[index],
                            onFocusChanged = { isFocused ->
                                if(isFocused) {
                                    onAction(AuthenticationAction.OnChangeFieldFocused(index))
                                }
                            },
                            isError = state.showIncorrectCodeMessage,
                            onNumberChanged = { newNumber ->
                                onAction(AuthenticationAction.OnEnterNumber(newNumber, index))
                            },
                            onKeyboardBack = {
                                onAction(AuthenticationAction.OnKeyboardBack)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = state.showIncorrectCodeMessage,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.incorrect_code_please_try_again),
                    style = MaterialTheme.typography.body4Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = state.buttonText.asString(),
                enabled = state.isPhoneNumberValid,
                onClick = {
                    onAction(AuthenticationAction.OnPrimaryButtonClick)
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextButton(
                text = stringResource(R.string.continue_without_signing_in),
                onClick = {}
            )
            if(state.isVerificationPhase) {
                if(state.showResendTimer) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.you_can_request_a_new_code_in_00,
                            state.remainingTime
                        ),
                        style = MaterialTheme.typography.body3Regular.copy(
                            fontFeatureSettings = "tnum",
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.textSecondary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    TextButton(
                        text = stringResource(R.string.resend_code),
                        onClick = {}
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun AuthenticationScreenPreview() {
    LazyPizzaTheme {
        AuthenticationScreen(
            state = AuthenticationState(
                isVerificationPhase = true
            ),
            onAction = {}
        )
    }
}