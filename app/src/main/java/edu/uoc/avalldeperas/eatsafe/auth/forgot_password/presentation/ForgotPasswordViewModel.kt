package edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.auth.forgot_password.domain.use_cases.ValidateForgotPasswordUseCase
import edu.uoc.avalldeperas.eatsafe.auth.login.data.AuthRepository
import edu.uoc.avalldeperas.eatsafe.common.util.ToastUtil.showToast
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

    fun onForgotClick(context: Context) {
        val validationResult = validateForgotPasswordInputUseCase(_email.value)

        if (!validationResult.isValid) {
            showToast(validationResult.message!!, context)
            return
        }

        viewModelScope.launch {
            val errorMessage = authRepository.passwordReset(_email.value)
            if (errorMessage.isNotEmpty()) {
                showToast(errorMessage, context)
                return@launch
            }

            showToast("Recovery password email sent", context)
        }

    }
}