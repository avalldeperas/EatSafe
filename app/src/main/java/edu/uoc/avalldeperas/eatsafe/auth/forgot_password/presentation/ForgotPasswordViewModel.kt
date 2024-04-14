package edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.domain.ForgotPasswordInputValidationType
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.domain.use_cases.ValidateForgotPasswordUseCase
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateForgotPasswordInputUseCase: ValidateForgotPasswordUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun onForgotClick(showConfirmation: () -> Job) {
        val validationResult = validateForgotPasswordInputUseCase(_email.value)
        Log.d(
            "avb", "validation result: ${validationResult.isValid} and ${validationResult.message}"
        )
        if (validationResult == ForgotPasswordInputValidationType.Valid) {
            viewModelScope.launch {
                val authResult = authRepository.passwordReset(_email.value)
                if (authResult) {
                    showConfirmation()
                } else {
                    Log.d("avb", "onLoginClick: failed result")
                }
            }
        }
    }
}