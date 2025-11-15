package com.jvrcoding.lazypizza.auth.presentation.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.jvrcoding.lazypizza.auth.domain.UserDataValidator
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit


private const val TAG = "FIREBASE_AUTH"
class AuthenticationViewModel(
    private val userDataValidator: UserDataValidator,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var hasLoadedInitialData = false

    private val phoneNumber = MutableStateFlow("")
    private var verificationId = ""

    private val eventChannel = Channel<AuthenticationEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                Log.d("currentUsr", firebaseAuth.currentUser?.uid.toString())
                observeTextFields()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AuthenticationState()
        )

    private var resendTimerJob: Job? = null

    fun onAction(action: AuthenticationAction) {
        when(action) {
            is AuthenticationAction.OnChangeFieldFocused -> {
                _state.update { it.copy(
                    focusedIndex = action.index
                ) }
            }
            is AuthenticationAction.OnEnterNumber -> {
                Log.d("awit", "${action.number} ${action.index}")
                enterNumber(action.number, action.index)
            }
            AuthenticationAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update { it.copy(
                    code = it.code.mapIndexed { index, number ->
                        if(index == previousIndex) {
                            null
                        } else {
                            number
                        }
                    },
                    focusedIndex = previousIndex
                ) }
            }

            is AuthenticationAction.OnPhoneNumberChange -> onPhoneNumberChange(action.phoneNumber)
            AuthenticationAction.OnPrimaryButtonClick -> {
                if(state.value.isVerificationPhase) {
                    signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(
                        verificationId,
                        state.value.code.joinToString("")
                    ))
                } else {
                    sendVerificationCode()
                }
            }
        }
    }

    private fun observeTextFields() {
        phoneNumber
            .onEach { phoneNumber ->
                val isValidPhoneNumber = userDataValidator.isValidPhoneNumber(phoneNumber) && phoneNumber.isNotBlank()
                _state.update { it.copy(
                    isPhoneNumberValid = isValidPhoneNumber
                ) }
            }.launchIn(viewModelScope)
    }

    private fun onPhoneNumberChange(text: String) {
        phoneNumber.value = text
        _state.update { it.copy(
            phoneNumber = text
        ) }
    }

    private fun sendVerificationCode() {
//        val firebaseAuthSettings = firebaseAuth.firebaseAuthSettings
//        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber("+639234566321", "123456")
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+639234566321")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    _state.update { it.copy(
                        isVerificationPhase = true,
                        code = credential.smsCode?.map { it.digitToInt() } ?: listOf()
                    ) }
                    signInWithPhoneAuthCredential(credential)
                    Log.d(TAG, "onVerificationCompleted: $credential")

                }
                override fun onVerificationFailed(e: FirebaseException) {
                    Log.d(TAG, "onVerificationFailed: $e")

                }
                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = id
                    _state.update { it.copy(
                        isVerificationPhase = true
                    ) }
                    startResendCountdown()
                }

                override fun onCodeAutoRetrievalTimeOut(p0: String) {
                    Log.d(TAG, "onCodeAutoRetrievalTimeOut: $p0")
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startResendCountdown() {
        resendTimerJob?.cancel()

        resendTimerJob = viewModelScope.launch {
            val totalSeconds = 10

            for (time in totalSeconds downTo 1) {
                delay(1000)
                _state.update { it.copy(
                    remainingTime = time - 1
                ) }
            }

            _state.update { it.copy(
                showResendTimer = false
            ) }
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = state.value.code.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _state.update { it.copy(
            code = newCode,
            focusedIndex = if(wasNumberRemoved || it.code.getOrNull(index) != null) {
                it.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = it.code,
                    currentFocusedIndex = it.focusedIndex
                )
            }
        ) }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        viewModelScope.launch {
            try {
                val result = firebaseAuth.signInWithCredential(credential).await()

                val user = result.user
                Log.d(TAG, "signInWithCredential:success")

                eventChannel.send(AuthenticationEvent.SuccessfulAuthentication)

            } catch (e: Exception) {
                Log.w(TAG, "signIn failure", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    _state.update { it.copy(showIncorrectCodeMessage = true) }
                } else {
//                    _events.emit(AuthEvent.Error(e.localizedMessage ?: "Something went wrong"))
                }
            }
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 5) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }
}